package com.es.phoneshop.model.services;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.exceptions.OutOfStockException;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    Cart getCart(HttpServletRequest req);
    void add(HttpServletRequest req, long productId, int quantity) throws OutOfStockException;
}
