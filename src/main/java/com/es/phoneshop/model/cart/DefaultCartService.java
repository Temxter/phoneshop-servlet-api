package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.dao.impl.ArrayListProductDao;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class DefaultCartService implements CartService {

    private final String CARD_SESSION_ATTRIBUTE = DefaultCartService.class.getName() + ".cart";

    private static DefaultCartService instance;

    private DefaultCartService() {
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

        Product product = ArrayListProductDao.getInstance().getProduct(productId);
        List<CartItem> cartList = cart.getItemList();
        Optional<CartItem> optionalItem = cartList.stream()
                .filter(item -> item.getProduct().equals(product))
                .findAny();

        if (optionalItem.isPresent()) {
            CartItem item = optionalItem.get();
            int totalQuantity = item.getQuantity() + quantity;
            if (totalQuantity <= product.getStock()) {
                item.setQuantity(totalQuantity);
            } else {
                throw new OutOfStockException(String
                        .format("Item quantity [= %d] more than stock [= %d] of item!", totalQuantity, product.getStock()));
            }
        }
        else {
            if (quantity <= product.getStock()) {
                cart.add(new CartItem(product, quantity));
            }
            else {
                throw new OutOfStockException(String
                        .format("Item quantity [= %d] more than stock [= %d] of item!", quantity, product.getStock()));
            }
        }

    }
}
