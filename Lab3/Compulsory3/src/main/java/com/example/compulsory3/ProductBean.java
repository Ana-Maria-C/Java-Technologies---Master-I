package com.example.compulsory3;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;

import java.util.ArrayList;
import java.util.List;

@ManagedBean(name="productBean")
@SessionScoped
public class ProductBean {
    private List<Product> products ;
    private final ProductRepository productRepository = new ProductRepository();

    public List<Product> getProducts() {
        if (products == null) {
            products = productRepository.findAll();
        }
        return products;
    }
}