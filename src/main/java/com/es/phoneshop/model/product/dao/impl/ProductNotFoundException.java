package com.es.phoneshop.model.product.dao.impl;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super(id.toString());
    }
}
