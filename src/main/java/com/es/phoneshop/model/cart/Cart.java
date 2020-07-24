package com.es.phoneshop.model.cart;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> itemList;

    public Cart() {
        itemList = new ArrayList<>();
    }

    public List<CartItem> getItemList() {
        return itemList;
    }

    public void add(CartItem cartItem) {
        itemList.add(cartItem);
    }
}
