package com.es.phoneshop.comparators;

import com.es.phoneshop.model.product.Product;

import java.util.Comparator;

public class ProductDescriptionComparator implements Comparator<Product> {

    @Override
    public int compare(Product first, Product second) {
        return first.getDescription().compareTo(second.getDescription());
    }
}
