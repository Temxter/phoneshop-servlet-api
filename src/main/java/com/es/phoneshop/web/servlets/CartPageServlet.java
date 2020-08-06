package com.es.phoneshop.web.servlets;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.services.CartService;
import com.es.phoneshop.services.impl.DefaultCartService;
import com.es.phoneshop.services.impl.DefaultRecentlyViewedProductsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CartPageServlet extends HttpServlet {

    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("cart", cartService.getCart(req));
        req.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] quantities = req.getParameterValues("quantity");
        String[] productIds = req.getParameterValues("productId");
        Map<Long, String> errors = new HashMap<>();
        for (int i = 0; i < productIds.length; i++) {
            Long productId = Long.parseLong(productIds[i]);
            try {
                cartService.update(req, productId, Integer.parseInt(quantities[i]));
            } catch (OutOfStockException | NumberFormatException e) {
                errors.put(productId, e.getMessage());
            }
        }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            doGet(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/cart"
                    + "?message=Cart updated successfully!");
        }

    }
}
