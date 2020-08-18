package com.es.phoneshop.comparators;

import com.es.phoneshop.model.product.Product;

import java.util.Comparator;

public class ProductRelevantSearchComparator implements Comparator<Product> {

    private final String[] splitQuery;

    public ProductRelevantSearchComparator(String[] splitQuery) {
        this.splitQuery = splitQuery;
    }

    @Override
    public int compare(Product first, Product second) {
        int firstCounter = 0, secondCounter = 0;
        for (String q : splitQuery) {
            if (first.getDescription().toLowerCase().contains(q))
                firstCounter++;
            if (second.getDescription().toLowerCase().contains(q))
                secondCounter++;
        }
        return firstCounter - secondCounter;
    }
}
