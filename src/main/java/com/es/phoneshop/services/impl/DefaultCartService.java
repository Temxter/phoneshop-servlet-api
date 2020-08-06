package com.es.phoneshop.services.impl;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.services.CartService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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
        changeProductInCart(req, productId, quantity, false);

    }

    @Override
    public void update(HttpServletRequest req, long productId, int quantity) throws OutOfStockException {
        if (quantity == 0) {
            delete(req, productId);
        }
        changeProductInCart(req, productId, quantity, true);
    }

    @Override
    public void delete(HttpServletRequest req, long productId) {
        Cart cart = getCart(req);
        List<CartItem> cartList = cart.getItemList();
        for (CartItem item : cartList) {
            if (item.getProduct().getId().equals(productId)) {
                item.getProduct().setStock(item.getProduct().getStock() + item.getQuantity());
                cartList.remove(item);
                break;
            }
        }
        recalculateCart(cart);
        saveList(req, cart);
    }

    private void changeProductInCart(HttpServletRequest req, long productId, int quantity, boolean isUpdate) throws OutOfStockException {
        Cart cart = getCart(req);

        List<CartItem> cartList = cart.getItemList();
        Optional<CartItem> optionalItem = cartList.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findAny();

        boolean newItem = false;
        CartItem item = null;
        if (optionalItem.isPresent()) {
            item = optionalItem.get();
        } else {
            item = new CartItem(productDao.getProduct(productId), 0);
            newItem = true;
        }

        lock.lock();
        try {
            int realStock = item.getProduct().getStock();
            int totalClientQuantity = isUpdate
                    ? quantity
                    : quantity + item.getQuantity();
            if (totalClientQuantity - item.getQuantity() <= realStock) {
                item.getProduct().setStock(isUpdate
                        ? realStock - quantity + item.getQuantity()
                        : realStock - quantity);
                item.setQuantity(totalClientQuantity);
                if (newItem) {
                    cart.add(item);
                }
                recalculateCart(cart);
                saveList(req, cart);
            } else {
                throw new OutOfStockException(String
                        .format("Item quantity [= %d] more than stock [= %d] of item!",
                                quantity,
                                productDao.getProduct(productId).getStock()));
            }
        } finally {
            lock.unlock();
        }
    }

    private void recalculateCart(Cart cart) {
        BigDecimal totalCost = cart.getItemList().stream()
                .reduce(new BigDecimal(0),
                        (acc, item) -> acc
                        .add(item.getProduct().getProductPrice().getPrice()
                                .multiply(new BigDecimal(item.getQuantity()))),
                        (bigDecimal, bigDecimal2) -> bigDecimal.add(bigDecimal2));
        int totalQuantity = cart.getItemList().stream()
                .reduce(0,
                        (acc, item) -> acc += item.getQuantity(),
                        (integer, integer2) -> integer + integer2);
        cart.setTotalCost(totalCost);
        cart.setTotalQuantity(totalQuantity);
    }

    private void saveList(HttpServletRequest req, Cart cart) {
        req.getSession().setAttribute(CARD_SESSION_ATTRIBUTE, cart);
    }
}
