package com.es.phoneshop.model.services.impl;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartItem;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.services.CartService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class DefaultCartService implements CartService {

    private final String CARD_SESSION_ATTRIBUTE = DefaultCartService.class.getName() + ".cart";

    private static DefaultCartService instance;

    private final ArrayListProductDao productDao;

    private final ReentrantLock lock;

    private DefaultCartService() {
        productDao = ArrayListProductDao.getInstance();
        lock = new ReentrantLock();
    }

    public static synchronized DefaultCartService getInstance() {
        if (instance == null) {
            instance = new DefaultCartService();
        }
        return instance;
    }

    @Override
    public Cart getCart(HttpServletRequest req) {
        Cart cart = (Cart) req.getSession().getAttribute(CARD_SESSION_ATTRIBUTE);
        if (cart == null) {
            cart = new Cart();
            req.getSession().setAttribute(CARD_SESSION_ATTRIBUTE, cart);
        }
        return cart;
    }

    @Override
    public void add(HttpServletRequest req, long productId, int quantity) throws OutOfStockException {
        Cart cart = getCart(req);

        Product product = productDao.getProduct(productId);
        List<CartItem> cartList = cart.getItemList();
        Optional<CartItem> optionalItem = cartList.stream()
                .filter(item -> item.getProduct().equals(product))
                .findAny();

        boolean newItem = false;
        CartItem item = null;
        if (optionalItem.isPresent()) {
            item = optionalItem.get();
        } else {
            item = new CartItem(product, 0);
            newItem = true;
        }

        lock.lock();
        int realStock = item.getProduct().getStock();
        int totalClientQuantity = quantity + item.getQuantity();
        if (totalClientQuantity <= realStock) {
            item.getProduct().setStock(realStock - quantity);
            lock.unlock();
            item.setQuantity(totalClientQuantity);
            if (newItem) {
                cart.add(item);
            }
            saveList(req, cart);
        } else {
                throw new OutOfStockException(String
                        .format("Item quantity [= %d] more than stock [= %d] of item!",
                                totalClientQuantity,
                                product.getStock()));
        }
    }

    private void saveList(HttpServletRequest req, Cart cart) {
        req.getSession().setAttribute(CARD_SESSION_ATTRIBUTE, cart);
    }
}