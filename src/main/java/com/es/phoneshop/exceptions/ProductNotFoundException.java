package com.es.phoneshop.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super(id.toString());
    }
}
