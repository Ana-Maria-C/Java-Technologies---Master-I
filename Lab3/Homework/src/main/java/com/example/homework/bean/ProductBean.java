package com.example.homework.bean;

import com.example.homework.repository.ProductRepository;
import com.example.homework.model.Product;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;

import java.util.List;

@ManagedBean(name="productBean")
@SessionScoped
public class ProductBean {
    private List<Product> products;
    private final ProductRepository productRepository = new ProductRepository();

    public List<Product> getProducts() {
        if (products == null) {
            products = productRepository.findAll();
        }
        return products;
    }
}