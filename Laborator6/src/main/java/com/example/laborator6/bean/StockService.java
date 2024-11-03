package com.example.laborator6.bean;

import com.example.laborator6.repository.ProductRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import com.example.laborator6.model.Product;

@Stateless
public class StockService {

    @EJB
    private ProductRepository productRepository;

    public int getStock(Long productId) {
        Product product = productRepository.findById(productId);
        return product != null ? product.getStockQuantity() : 0;
    }

    public void reduceStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId);
        if (product != null && product.getStockQuantity() >= quantity) {
            product.setStockQuantity(product.getStockQuantity() - quantity);
            productRepository.update(product);
        }
    }
}
