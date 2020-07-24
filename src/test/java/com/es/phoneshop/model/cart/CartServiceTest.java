package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.ProductSaveSampleData;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CartServiceTest {

    private CartService cartService;

    @Before
    public void init() {
        cartService = DefaultCartService.getInstance();
        ProductSaveSampleData.saveSampleProducts();
    }

    @Test
    public void testSetItem() throws OutOfStockException {
        cartService.add(1L, 1);
        assertFalse("Item not added to cart!", cartService.getCart().getItemList().isEmpty());
    }

    @Test(expected = OutOfStockException.class)
    public void testSetItemException() throws OutOfStockException {
        cartService.add(1L, Integer.MAX_VALUE);
    }
}
