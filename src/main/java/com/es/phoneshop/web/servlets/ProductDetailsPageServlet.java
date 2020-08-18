package com.es.phoneshop.web.servlets;

import com.es.phoneshop.services.CartService;
import com.es.phoneshop.services.impl.DefaultCartService;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.services.impl.DefaultRecentlyViewedProductsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class ProductDetailsPageServlet extends HttpServlet {

    private ArrayListProductDao productDao;
    private CartService cartService;
    private DefaultRecentlyViewedProductsService recentlyViewedService;

    private Product getProduct(HttpServletRequest req) {
        String path = req.getPathInfo();
        long productId = Long.parseLong(path.substring(1));
        Product product = productDao.getProduct(productId);
        recentlyViewedService.addProduct(req, product);
        req.setAttribute("product", product);
        req.setAttribute("recentlyViewedProducts", recentlyViewedService.getList(req));
        return product;
    }

    private void setCart(HttpServletRequest req){
        req.setAttribute("cart", cartService.getCart(req).getItemList().toString());
    }

    @Override
    public void init() throws ServletException {
        super.init();
        productDao = ArrayListProductDao.getInstance();
        cartService = DefaultCartService.getInstance();
        recentlyViewedService = DefaultRecentlyViewedProductsService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getProduct(req);
        setCart(req);
        req.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product = getProduct(req);
        long productId = product.getId();
        String returnMainPage = req.getParameter("returnMainPage");
        boolean isReturnMainPage = false;
        if (returnMainPage != null && returnMainPage.equals("true")) {
            isReturnMainPage = true;
        }
        int quantity;
        Locale locale = req.getLocale();
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        try {
            quantity = numberFormat.parse(req.getParameter("quantity")).intValue();
        } catch (ParseException e) {
            if (isReturnMainPage) {
                resp.sendRedirect(String.format("%s/products?error=" +
                        "It is not a number!&id=%d&quantity=%s",
                        req.getContextPath(), productId, req.getParameter("quantity")));
            } else {
                req.setAttribute("error", "It is not a number");
                doGet(req, resp);
            }
            return;
        }
        req.setAttribute("quantity", quantity);
        try {
            cartService.add(req, productId, quantity);
        }  catch (OutOfStockException e) {
            if (isReturnMainPage) {
                resp.sendRedirect(String.format("%s/products?error=" +
                        "%s&id=%d&quantity=%d",
                        req.getContextPath(), e.getMessage(), productId, quantity));
            } else {
                req.setAttribute("error", e.getMessage());
                doGet(req, resp);
            }
            return;
        }
        setCart(req);

        if (isReturnMainPage) {
            resp.sendRedirect(String.format("%s/products?message=%s added to card&id=%d&quantity=%d",
                    req.getContextPath(), product.getCode(), productId, quantity));
        } else {
            resp.sendRedirect(String.format("%s/products/%d?message=%s added to card successfully!",
                    req.getContextPath(), product.getId(), product.getCode()));
        }
    }
}
