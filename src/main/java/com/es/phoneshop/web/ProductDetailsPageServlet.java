package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        long productId = Long.parseLong(path.substring(1));
        Product product = ArrayListProductDao.getInstance().getProduct(productId);
        req.setAttribute("product", product);

        req.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(req, resp);
    }
}
