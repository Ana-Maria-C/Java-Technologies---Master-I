package com.example.compulsory3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    // Database connection parameters
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/product_DB";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "AnaMaria22!";

    // JDBC connection method
    public Connection connect() {
        Connection connection = null;
        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
            // Establish the connection
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("PostgreSQL JDBC Driver not found.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection failed. Check output console.");
        }
        return connection;
    }

    // Method to retrieve all products from the database
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, name, price, description FROM products";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Process the result set
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
