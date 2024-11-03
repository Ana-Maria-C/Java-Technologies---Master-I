package com.example.laborator6.bean;

import com.example.laborator6.log.LogInvocation;
import com.example.laborator6.model.Client;
import com.example.laborator6.model.Order;
import com.example.laborator6.model.Product;
import com.example.laborator6.repository.OrderRepository;
import com.example.laborator6.repository.ProductRepository;
import jakarta.ejb.Stateful;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@Stateful
public class OrderService {
    @Inject
    private StockService stockService;

    @Inject
    private ProductRepository productRepository;

    @Inject
    private OrderRepository orderRepository;

    @Inject
    private OrderCache orderCache;

    private Order currentOrder;

    public void startOrder() {
        currentOrder = new Order();
        currentOrder.setOrderDate(new Date());
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addProductToOrder(Long productId, int quantity) throws Exception {
        if (stockService.getStock(productId) < quantity) {
            throw new Exception("Insufficient stock for product: " + productId);
        }

        Product product = productRepository.findById(productId);
        if (product == null) {
            throw new Exception("Product not found: " + productId);
        }

        currentOrder.getProductQuantities().put(product, quantity);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @LogInvocation
    public Order completeOrder() throws Exception {
        for (Map.Entry<Product, Integer> entry : currentOrder.getProductQuantities().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            if (stockService.getStock(product.getId()) < quantity) {
                throw new Exception("Insufficient stock for product: " + product.getId());
            }

            stockService.reduceStock(product.getId(), quantity);
        }

        Order createdOrder = orderRepository.create(currentOrder);
        orderCache.addOrder(createdOrder);
        return createdOrder;
    }

    public Product getProductById(Long productId){
       return productRepository.findById(productId);
    }

    public void updateOrder(Order order) {
        orderRepository.update(order);
    }
}
