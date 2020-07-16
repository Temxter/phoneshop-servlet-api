package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse("productDao is empty!", productDao.findProducts().isEmpty());
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
}
