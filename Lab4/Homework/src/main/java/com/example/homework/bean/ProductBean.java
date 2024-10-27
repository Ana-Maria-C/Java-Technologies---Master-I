package com.example.homework.bean;

import com.example.homework.model.Product;
import jakarta.annotation.Resource;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name="productBean")
@SessionScoped
public class ProductBean {
    private List<Product> products;
    @Resource(name = "jdbc/PostgresPool")
    private DataSource ds;

    public List<Product> getProducts() {

        products = new ArrayList<>() ;
        String sql = "SELECT id, name, price, description FROM products";

        try (Connection conn = ds.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                products.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
}