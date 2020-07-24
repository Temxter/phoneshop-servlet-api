package com.es.phoneshop.model.viewedproducts;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DefaultRecentlyViewedProductsService implements RecentlyViewedProductsService {

    private final String RECENTLY_VIEWED_ATTRIBUTE
            = DefaultRecentlyViewedProductsService.class.getName() + ".viewedProducts";

    private DefaultRecentlyViewedProductsService() {}

    private static class BillPughSingleton {
        private static final DefaultRecentlyViewedProductsService INSTANCE = new DefaultRecentlyViewedProductsService();
    }

    public static DefaultRecentlyViewedProductsService getInstance() {
        return BillPughSingleton.INSTANCE;
    }

    @Override
    public List<Product> getList(HttpServletRequest req) {
        LinkedList<Product> products = (LinkedList) req.getSession().getAttribute(RECENTLY_VIEWED_ATTRIBUTE);
        if (products == null) {
            products = new LinkedList<>();
            req.getSession().setAttribute(RECENTLY_VIEWED_ATTRIBUTE, products);
        }
        return products;
    }

    @Override
    public void addProduct(HttpServletRequest req, Product product) {
        final int maxSize = 3;
        LinkedList<Product> productLinkedList = (LinkedList<Product>) getList(req);
        if (productLinkedList.contains(product)) {
            return;
        }
        if (productLinkedList.size() >= maxSize) {
            productLinkedList.removeLast();
        }
        productLinkedList.addFirst(product);
        saveProduct(req, productLinkedList);
    }

    private void saveProduct(HttpServletRequest req, LinkedList<Product> productList) {
        req.getSession().setAttribute(RECENTLY_VIEWED_ATTRIBUTE, productList);
    }
}
