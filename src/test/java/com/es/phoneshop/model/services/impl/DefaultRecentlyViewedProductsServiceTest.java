package com.es.phoneshop.model.services.impl;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.services.impl.DefaultRecentlyViewedProductsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ ArrayListProductDao.class })
public class DefaultRecentlyViewedProductsServiceTest {

    private DefaultRecentlyViewedProductsService service;

    private final ArrayListProductDao productDao = mock(ArrayListProductDao.class);

    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpSession httpSession;
    @Mock
    private Product product;

    private LinkedList<Product> productLinkedList;

    @Before
    public void init() {
        service = DefaultRecentlyViewedProductsService.getInstance();
        productLinkedList = new LinkedList<>();
        Mockito.when(req.getSession()).thenReturn(httpSession);
        Mockito.when(httpSession.getAttribute(Mockito.anyString())).thenReturn(productLinkedList);
        Whitebox.setInternalState(ArrayListProductDao.class, "arrayListProductDao", productDao);
        Mockito.when(productDao.getProduct(Mockito.anyLong())).thenReturn(product);
    }

    @Test
    public void testAddProduct() {
        Product product = ArrayListProductDao.getInstance().getProduct(0L);
        service.addProduct(req, product);
        assertTrue(productLinkedList.contains(product));
    }
}
