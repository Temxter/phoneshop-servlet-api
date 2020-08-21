package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.exceptions.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;

import java.util.ArrayList;
import java.util.List;

public class ArrayListOrderDao implements OrderDao {

    private static ArrayListOrderDao arrayListOrderDao;

    private final List<Order> orderList;
    private long maxId = 0L;

    private ArrayListOrderDao() {
        orderList = new ArrayList<>();
    }

    public static synchronized ArrayListOrderDao getInstance() {
        if (arrayListOrderDao == null)
            arrayListOrderDao = new ArrayListOrderDao();
        return arrayListOrderDao;
    }

    @Override
    public Order getOrder(Long id) {
        return orderList.stream()
                .filter(order -> id.equals(order.getId()))
                .findAny()
                .orElseThrow(() -> new OrderNotFoundException(id.toString()));
    }

    @Override
    synchronized public void save(Order order) {
        if (order.getId() != null) {
            for (int i = 0; i < orderList.size(); i++) {
                Order gotOrder = orderList.get(i);
                if (gotOrder.getId() == order.getId()) {
                    orderList.set(i, order);
                    return;
                }
            }
        }
        order.setId(maxId++);
        orderList.add(order);
    }

    @Override
    public Order getOrderBySecureId(String secureId) {
        return orderList.stream()
                .filter(order -> secureId.equals(order.getSecureId()))
                .findAny()
                .orElseThrow(() -> new OrderNotFoundException(secureId));
    }
}
