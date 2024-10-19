package com.example.homework.dataBaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    // database connection parameters
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/product_DB";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "AnaMaria22!";


    public static Connection getConnection() {
        Connection connection = null;
        try {
            // load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
            // establish  connection
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
}