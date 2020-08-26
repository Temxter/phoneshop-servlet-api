package com.es.phoneshop.web.servlets;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvancedSearchProductPageServlet extends HttpServlet {

    private ArrayListProductDao arrayListProductDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        arrayListProductDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productCode = checkValueOfParameter(request, "productCode");

        Map<String, String> errors = new HashMap<>();

        BigDecimal minPrice = parsePrice(request, "minPrice", errors);
        BigDecimal maxPrice = parsePrice(request, "maxPrice", errors);
        Integer minStock = getMinStock(request, errors);

        List<Product> foundProducts = arrayListProductDao.findProductsByFields(productCode, minPrice, maxPrice, minStock);

        if (errors.isEmpty()) {
            request.setAttribute("products", foundProducts);
            if (productCode != null || minPrice != null || maxPrice != null || minPrice != null) {
                request.setAttribute("message", String.format("Found %d product"
                                + (foundProducts.size() > 1 ? "s" : ""),
                        foundProducts.size()));
            }
        } else {
            request.setAttribute("products", foundProducts);
            request.setAttribute("errors", errors);
        }

        request.getRequestDispatcher("/WEB-INF/pages/advancedSearchProduct.jsp").forward(request, response);
    }

    private String checkValueOfParameter(HttpServletRequest req, String parameter) {
        String value = req.getParameter(parameter);
        if (value == null || value.isEmpty()) {
            return null;
        } else {
            req.setAttribute(parameter, value);
            return value;
        }
    }

    private BigDecimal parsePrice(HttpServletRequest req, String parameter, Map<String, String> errors) {
        String value = checkValueOfParameter(req, parameter);
        if (value != null) {
            BigDecimal price = null;
            try {
                price = new BigDecimal(value);
            } catch (NumberFormatException e) {
                errors.put(parameter, "Not a number");
            }
            return price;
        }
        return null;
    }

    private Integer getMinStock(HttpServletRequest req, Map<String, String> errors) {
        String parameter = "minStock";
        String value = checkValueOfParameter(req, parameter);
        if (value != null) {
            Integer minStock = null;
            try {
                minStock = new Integer(value);
            } catch (NumberFormatException e) {
                errors.put(parameter, "Not a number");
            }
            return minStock;
        }
        return null;
    }

}
