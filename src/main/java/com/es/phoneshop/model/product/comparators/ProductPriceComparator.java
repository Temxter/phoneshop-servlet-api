package com.es.phoneshop.model.product.comparators;

import com.es.phoneshop.model.product.Product;

import java.util.Comparator;

public class ProductPriceComparator implements Comparator<Product> {

    @Override
    public int compare(Product first, Product second) {
        return first.getPrice().subtract(second.getPrice()).intValue();
    }
}
