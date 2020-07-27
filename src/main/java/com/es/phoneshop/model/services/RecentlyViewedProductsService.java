package com.es.phoneshop.model.services;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface RecentlyViewedProductsService {
    List<Product> getList(HttpServletRequest req);
    void addProduct(HttpServletRequest req, Product product);
}
