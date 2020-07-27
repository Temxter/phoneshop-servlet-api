package com.es.phoneshop.cart;

import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.ProductSaveSampleData;
import com.es.phoneshop.model.services.CartService;
import com.es.phoneshop.model.services.impl.DefaultCartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {

    private CartService cartService;
    @Mock
    HttpServletRequest req;
    @Mock
    HttpSession httpSession;
    Cart cart;


    @Before
    public void init() {
        cartService = DefaultCartService.getInstance();
        cart = new Cart();
        ProductSaveSampleData.saveSampleProducts();
        Mockito.when(req.getSession()).thenReturn(httpSession);
        Mockito.when(httpSession.getAttribute(Mockito.anyString())).thenReturn(cart);
    }

    @Test
    public void testSetItem() throws OutOfStockException {
        cartService.add(req, 0L, 1);
        assertFalse("Item not added to cart!", cartService.getCart(req).getItemList().isEmpty());
    }

    @Test(expected = OutOfStockException.class)
    public void testSetItemException() throws OutOfStockException {
        cartService.add(req, 1L, Integer.MAX_VALUE);
    }
}
