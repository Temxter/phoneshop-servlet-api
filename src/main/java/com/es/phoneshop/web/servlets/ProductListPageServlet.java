package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.product.*;
import com.es.phoneshop.model.product.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.enums.SortField;
import com.es.phoneshop.model.product.enums.SortOrder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ProductListPageServlet extends HttpServlet {

    private ArrayListProductDao arrayListProductDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        arrayListProductDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String field = request.getParameter("field");
        String order = request.getParameter("order");
        request.setAttribute("sort", field);
        request.setAttribute("order", order);
        request.setAttribute("query", query);

        List<Product> productList = arrayListProductDao.findProducts(query,
                    Optional.ofNullable(field).map(SortField::valueOf).orElse(null),
                    Optional.ofNullable(order).map(SortOrder::valueOf).orElse(null));


        request.setAttribute("products", productList);
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }


}
