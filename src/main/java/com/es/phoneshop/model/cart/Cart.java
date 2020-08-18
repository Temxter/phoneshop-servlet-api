package com.es.phoneshop.model.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
    private final List<CartItem> itemList;
    private int totalQuantity;
    private BigDecimal totalCost;

    public Cart() {
        itemList = new ArrayList<>();
        totalCost = new BigDecimal(0);
    }

    public List<CartItem> getItemList() {
        return itemList;
    }

    public void add(CartItem cartItem) {
        itemList.add(cartItem);
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "Cart = " + itemList.toString();
    }
}
