package com.es.phoneshop.model.cart;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    Cart getCart(HttpServletRequest req);
    void add(HttpServletRequest req, long productId, int quantity) throws OutOfStockException;
}
