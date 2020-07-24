package com.es.phoneshop.model.cart;

public class OutOfStockException extends Exception {
    public OutOfStockException() {
        super();
    }

    public OutOfStockException(String message) {
        super(message);
    }
}
