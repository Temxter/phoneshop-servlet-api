package com.es.phoneshop.model.cart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
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

    @Override
    public String toString() {
        return "Cart = " + itemList.toString();
    }
}
