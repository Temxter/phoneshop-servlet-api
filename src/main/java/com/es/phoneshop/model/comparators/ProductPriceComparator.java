package com.es.phoneshop.model.comparators;

import com.es.phoneshop.model.product.Product;

import java.util.Comparator;

public class ProductPriceComparator implements Comparator<Product> {

    @Override
    public int compare(Product first, Product second) {
        return first.getProductPrice().getPrice()
                .subtract(second.getProductPrice().getPrice())
                .intValue();
    }
}
