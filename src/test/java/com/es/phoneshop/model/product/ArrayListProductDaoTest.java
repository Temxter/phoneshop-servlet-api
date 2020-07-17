package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
        ProductSaveSampleData.saveSampleProducts();
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse("productDao is empty!", productDao.findProducts(null, null, null).isEmpty());
    }

    @Test
    public void testFindById() {
        long id = 1L;
        Product product = productDao.getProduct(id);
        assertNotNull("Not have product", product);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDeleteProduct() {
        long id = 1L;
        productDao.delete(id);
        productDao.getProduct(id);
    }

    private Product getProductSample() {
        Currency usd = Currency.getInstance("USD");
        Product newProduct = new Product("iphone10", "Apple iPhone 10", new BigDecimal(600), usd, 10, "https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/refurb-iphoneX-silver_FMT_WHH?wid=400&hei=400&fmt=jpeg&qlt=80&op_usm=0.5,0.5&.v=1546626276301");
        return newProduct;
    }

    @Test
    public void testSaveProduct() {
        Product newProduct = getProductSample();
        productDao.save(newProduct);
        Product gotProduct = productDao.getProduct(newProduct.getId());
        assertEquals("Saved product and got product from ArrayListProductDao", newProduct, gotProduct);
    }

    @Test
    public void testSaveExistProduct() {
        Product product = getProductSample();
        productDao.save(product);
        long productId = product.getId();
        Currency usd = Currency.getInstance("USD");
        Product otherProduct = new Product(productId, "other", "Apple iPhone 10", new BigDecimal(600), usd, 10, "https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/refurb-iphoneX-silver_FMT_WHH?wid=400&hei=400&fmt=jpeg&qlt=80&op_usm=0.5,0.5&.v=1546626276301");
        productDao.save(otherProduct);
        Product savedProduct = productDao.getProduct(productId);
        assertEquals(String.format("Product not updated! otherProduct.code = %s; otherProduct.code = %s.",
                otherProduct.getCode(), savedProduct.getCode()),
                otherProduct.getCode(),
                savedProduct.getCode());
    }

    @Test
    public void testSearchProducts() {
        String query = "Samsung III";
        List<Product> daoProducts = productDao.findProducts(query, null, null);
        List<String> expectDescriptions = new ArrayList<String>();
        expectDescriptions.add("Samsung Galaxy S III");
        expectDescriptions.add("Samsung Galaxy S II");
        expectDescriptions.add("Samsung Galaxy S");
        for (Product product : daoProducts) {
            Optional<String> result = expectDescriptions.stream()
                    .filter(description -> product.getDescription().equals(description))
                    .findAny();
            assertTrue("Search = [Samsung III] not corrected: LIST = " + daoProducts.stream()
                            .reduce("",
                                    (str, productStream) -> str + "[" + productStream.getDescription() + "] ",
                                    Objects::toString),
                    result.isPresent());
        }
    }

    @Test
    public void testSearchSort(){
        SortField sortField = SortField.price;
        SortOrder sortOrder = SortOrder.desc;
        List<Product> daoProducts = productDao.findProducts(null, sortField, sortOrder);
        List<BigDecimal> expectFirstPrices = new ArrayList<>();
        expectFirstPrices.add(new BigDecimal(1000));
        expectFirstPrices.add(new BigDecimal(420));
        expectFirstPrices.add(new BigDecimal(320));
        for (int i = 0; i < expectFirstPrices.size(); i++) {
            assertTrue("Sorting is not right!" + daoProducts.stream()
                            .reduce("",
                                    (str, productStream) -> str + "[" + productStream.getPrice() + "] ",
                                    Objects::toString),
                    expectFirstPrices.get(i).equals(daoProducts.get(i).getPrice()));
        }
    }
}
