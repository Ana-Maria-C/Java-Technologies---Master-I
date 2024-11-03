package com.example.laborator6.bean;

import com.example.laborator6.model.Client;
import com.example.laborator6.model.Order;
import com.example.laborator6.model.Product;
import com.example.laborator6.repository.OrderRepository;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named("orderBean")
@SessionScoped
public class OrderBean implements Serializable {
    @EJB
    private OrderService orderService;
    @EJB
    private OrderRepository orderRepository;
    private Order currentOrder;
    private Long selectedProductId;
    private int selectedQuantity;


    public void startNewOrder(Client client) {
        orderService.startOrder();
        currentOrder = orderService.getCurrentOrder();
        currentOrder.setClient(client);
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void addProduct(Long productId, int quantity) throws Exception {
        Product product = orderService.getProductById(productId);
        if (product != null) {
            orderService.addProductToOrder(productId, quantity);
        } else {
            throw new Exception("Product not found.");
        }
    }

    public List<Order> getClientOrders(){
        return orderRepository.findByClient(currentOrder.getClient());
    }

    public Long getSelectedProductId() {
        return selectedProductId;
    }

    public void setSelectedProductId(Long selectedProductId) {
        this.selectedProductId = selectedProductId;
    }

    public int getSelectedQuantity() {
        return selectedQuantity;
    }

    public void setSelectedQuantity(int selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }

    public void submitOrder() {
        try {
            Order completedOrder = orderService.completeOrder();
            getClientOrders();


            // start a new order
            currentOrder = new Order();
            orderService.startOrder();
            startNewOrder(completedOrder.getClient());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
