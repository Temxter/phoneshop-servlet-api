package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.comparators.ProductDescriptionComparator;
import com.es.phoneshop.model.product.comparators.ProductPriceComparator;
import com.es.phoneshop.model.product.comparators.ProductRelevantSearchComparator;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private List<Product> productList;
    private long maxId = 0L;

    public ArrayListProductDao() {
        productList = getSampleProducts();
    }

    @Override
    public Product getProduct(Long id) {
        return productList.stream()
                .filter(product -> id.equals(product.getId()))
                .findAny()
                .orElseThrow(() -> new ProductNotFoundException(String.format("Product with [id = %d] not founded.", id)));
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

    private List<Product> getSampleProducts() {
        List<Product> result = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        result.add(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        result.add(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        result.add(new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        result.add(new Product(4L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        result.add(new Product(5L, "iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        result.add(new Product(6L, "htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        result.add(new Product(7L, "sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        result.add(new Product(8L, "xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        result.add(new Product(9L, "nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
        result.add(new Product(10L, "palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
        result.add(new Product(11L, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        result.add(new Product(12L, "simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
        result.add(new Product(13L, "simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));
        maxId = 14L;
        return result;
    }
}
