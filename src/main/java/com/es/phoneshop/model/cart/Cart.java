package com.es.phoneshop.model.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class Cart implements Serializable {
    private List<CartItem> itemList;
    private int totalQuantity;
    private BigDecimal totalCost;
    private Currency currency;

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

    public void setItemList(List<CartItem> itemList) {
        this.itemList = itemList;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Cart = " + itemList.toString();
    }
}
