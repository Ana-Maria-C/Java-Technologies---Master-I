package com.example.laborator6.bean;

import com.example.laborator6.model.Product;
import com.example.laborator6.repository.ProductRepository;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;

import java.io.Serializable;
import java.util.List;

@Named("productBean")
@SessionScoped
public class ProductBean implements Serializable {
    @EJB
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
