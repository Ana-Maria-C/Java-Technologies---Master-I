package com.example.laborator6.bean;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import com.example.laborator6.model.Order;

import java.util.HashMap;
import java.util.Map;

@Startup
@Singleton
public class OrderCache {

    private Map<Long, Order> orderMap ;
    private long orderIdCounter; // unique order IDs

    @PostConstruct
    public void init() {
        orderMap = new HashMap<>();
        orderIdCounter = 0;
    }
    public void addOrder(Order order) {
        order.setId(++orderIdCounter);
        orderMap.put(order.getId(), order);
    }

    public Order getOrder(Long id) {
        return orderMap.get(id);
    }

    public Map<Long, Order> getAllOrders() {
        return new HashMap<>(orderMap);
    }
}
