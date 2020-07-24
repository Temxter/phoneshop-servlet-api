package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.DefaultCartService;
import com.es.phoneshop.model.cart.OutOfStockException;
import com.es.phoneshop.model.product.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

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

    @Override
    public void init() throws ServletException {
        super.init();
        productDao = ArrayListProductDao.getInstance();
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getProduct(req);
        setCart(req);
        req.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(req, resp);
    }

    private Product getProduct(HttpServletRequest req) {
        String path = req.getPathInfo();
        long productId = Long.parseLong(path.substring(1));
        Product product = productDao.getProduct(productId);
        req.setAttribute("product", product);
        return product;
    }

    private void setCart(HttpServletRequest req){
        req.setAttribute("cart", cartService.getCart().getItemList().toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product = getProduct(req);
        try {
            Locale locale = req.getLocale();
            NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
            int quantity = numberFormat.parse(req.getParameter("quantity")).intValue();
            req.setAttribute("quantity", quantity);
            long productId = product.getId();
            cartService.add(productId, quantity);
        } catch (ParseException e) {
            req.setAttribute("error", "It is not a number");
            doGet(req, resp);
            return;
        } catch (OutOfStockException e) {
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
            return;
        }
        setCart(req);

        resp.sendRedirect(req.getContextPath() + "/products/" + product.getId()
                + "?message=" + product.getCode() + " added to card successfully!");
    }
}
