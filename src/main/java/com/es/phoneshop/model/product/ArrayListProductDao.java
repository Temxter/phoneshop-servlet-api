package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.comparators.ProductDescriptionComparator;
import com.es.phoneshop.model.product.comparators.ProductPriceComparator;
import com.es.phoneshop.model.product.comparators.ProductRelevantSearchComparator;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private static ArrayListProductDao arrayListProductDao;

    private List<Product> productList;
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
        List<Product> foundProducts = productList;
        String[] splitQuery = null;
        // search
        if (query != null && !query.isEmpty()) {
            String[] finalSplitQuery = query.toLowerCase().split(" ");
            splitQuery = finalSplitQuery;
            foundProducts = productList.stream()
                    .filter(p -> {
                        for (String q : finalSplitQuery) {
                            if (p.getDescription().toLowerCase().contains(q))
                                return true;
                        }
                        return false;
                    })
                    .collect(Collectors.toList());
        }
        //sort
        if (sortField != null && sortOrder != null) {
            if (foundProducts == null)
                foundProducts = new ArrayList<>(productList);

            Comparator<Product> productComparator = null;
            if (sortField == SortField.description)
                productComparator = new ProductDescriptionComparator();
            else
                productComparator = new ProductPriceComparator();
            if (sortOrder == SortOrder.desc)
                productComparator = productComparator.reversed();

            foundProducts.sort(productComparator);
        } else if (splitQuery != null) {
            foundProducts.sort(new ProductRelevantSearchComparator(splitQuery).reversed());
        }
        return foundProducts;
    }
}
