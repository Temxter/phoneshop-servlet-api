package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.dao.ProductDao;
import com.es.phoneshop.model.product.dao.impl.ArrayListProductDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DefaultCartService implements CartService {

    private Cart cart;

    private static DefaultCartService instance;


    private DefaultCartService() {
        cart = new Cart();
    }

    public static synchronized DefaultCartService getInstance() {
        if (instance == null) {
            instance = new DefaultCartService();
        }
        return instance;
    }

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void add(long productId, int quantity) throws OutOfStockException {
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
