package com.es.phoneshop.services;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.exceptions.OutOfStockException;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    Cart getCart(HttpServletRequest req);
    void add(HttpServletRequest req, long productId, int quantity) throws OutOfStockException;
    void update(HttpServletRequest req, long productId, int quantity) throws OutOfStockException;
    void delete(HttpServletRequest req, long productId);
}
