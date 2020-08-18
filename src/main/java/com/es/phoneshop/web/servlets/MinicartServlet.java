package com.es.phoneshop.web.servlets;

import com.es.phoneshop.services.CartService;
import com.es.phoneshop.services.impl.DefaultCartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MinicartServlet extends HttpServlet {

    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("totalQuantity", cartService.getCart(req).getTotalQuantity());
        req.getRequestDispatcher("/WEB-INF/pages/minicart.jsp").include(req, resp);
    }
}
