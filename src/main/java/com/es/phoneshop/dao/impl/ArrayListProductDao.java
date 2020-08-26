package com.es.phoneshop.dao.impl;

import com.es.phoneshop.comparators.ProductDescriptionComparator;
import com.es.phoneshop.comparators.ProductPriceComparator;
import com.es.phoneshop.comparators.ProductRelevantSearchComparator;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.enums.SortField;
import com.es.phoneshop.enums.SortOrder;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private static ArrayListProductDao arrayListProductDao;

    private final List<Product> productList;
    private long maxId = 0L;

    private ArrayListProductDao() {
        productList = new ArrayList<>();
    }

    public static synchronized ArrayListProductDao getInstance() {
        if (arrayListProductDao == null)
            arrayListProductDao = new ArrayListProductDao();
        return arrayListProductDao;
    }

    @Override
    public Product getProduct(Long id) {
        return productList.stream()
                .filter(product -> id.equals(product.getId()))
                .findAny()
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    synchronized public void save(Product product) {
        if (product.getId() != null) {
            for (int i = 0; i < productList.size(); i++) {
                Product gotProduct = productList.get(i);
                if (gotProduct.getId() == product.getId()) {
                    productList.set(i, product);
                    return;
                }
            }
        }
        product.setId(maxId++);
        productList.add(product);
    }

    @Override
    synchronized public void delete(Long id) {
        productList.removeIf(product -> id.equals(product.getId()));
    }

    @Override
    public List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) {
        List<Product> foundProducts = null;
        String[] splitQuery = null;
        // search
        if (query != null && !query.isEmpty()) {
            String[] finalSplitQuery = query.toLowerCase().split(" ");
            splitQuery = finalSplitQuery;
            foundProducts = productList.stream()
                    .filter(p -> {
                        for (String q : finalSplitQuery) {
                            if (p.getDescription().toLowerCase().contains(q)) {
                                return true;
                            }
                        }
                        return false;
                    })
                    .collect(Collectors.toList());
        }
        //sort
        if (sortField != null && sortOrder != null) {
            if (foundProducts == null) {
                foundProducts = new ArrayList<>(productList);
            }

            Comparator<Product> productComparator = null;
            if (sortField == SortField.DESCRIPTION) {
                productComparator = new ProductDescriptionComparator();
            }
            else {
                    productComparator = new ProductPriceComparator();
                }
            if (sortOrder == SortOrder.DESC) {
                productComparator = productComparator.reversed();
            }

            foundProducts.sort(productComparator);
        } else if (splitQuery != null) {
            foundProducts.sort(new ProductRelevantSearchComparator(splitQuery).reversed());
        }
        return foundProducts == null
                ? productList
                : foundProducts;
    }

    public List<Product> findProductsByFields(String productCode, BigDecimal minPrice, BigDecimal maxPrice,
                                              Integer minStock) {
        List<Product> findProducts = productList;
        if (productCode != null && !productCode.isEmpty()) {
            findProducts = findProducts.stream()
                    .filter(product -> product.getCode().equals(productCode))
                    .collect(Collectors.toList());
        }
        if (minPrice != null) {
            findProducts = findProducts.stream()
                    .filter(product -> minPrice.compareTo(product.getProductPrice().getPrice()) <= 0)
                    .collect(Collectors.toList());
        }
        if (maxPrice != null) {
            findProducts = findProducts.stream()
                    .filter(product -> maxPrice.compareTo(product.getProductPrice().getPrice()) >= 0)
                    .collect(Collectors.toList());
        }
        if (minStock != null) {
            findProducts = findProducts.stream()
                    .filter(product -> product.getStock() >= minStock)
                    .collect(Collectors.toList());
        }
        return findProducts;
    }
}
