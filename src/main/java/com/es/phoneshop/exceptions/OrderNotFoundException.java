package com.es.phoneshop.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super(id.toString());
    }
}
