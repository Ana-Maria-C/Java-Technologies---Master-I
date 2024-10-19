package com.example.homework.repository;

import com.example.homework.dataBaseConnection.DataBaseConnection;
import com.example.homework.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository {
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT id, name, email, phone, address, dateOfBirth, gender FROM clients";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getLong("id"));
                client.setName(rs.getString("name"));
                client.setEmail(rs.getString("email"));
                client.setPhone(rs.getString("phone"));
                client.setAddress(rs.getString("address"));
                client.setDateOfBirth(rs.getDate("dateOfBirth"));
                client.setGender(rs.getString("gender"));
                clients.add(client);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return clients;
    }

    public void addClient(Client client){
        String sql = "INSERT INTO clients (name, email, phone, address, dateOfBirth, gender) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, client.getName());
            stmt.setString(2, client.getEmail());
            stmt.setString(3, client.getPhone());
            stmt.setString(4, client.getAddress());
            stmt.setDate(5, new java.sql.Date(client.getDateOfBirth().getTime()));
            stmt.setString(6, client.getGender());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateClient(Client client){

        String sql = "UPDATE clients SET name = ?, email = ?, phone = ?, address = ?, dateOfBirth = ?, gender = ?  WHERE id = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, client.getName());
            stmt.setString(2, client.getEmail());
            stmt.setString(3, client.getPhone());
            stmt.setString(4, client.getAddress());
            stmt.setDate(5, new java.sql.Date(client.getDateOfBirth().getTime()));
            stmt.setString(6, client.getGender());
            stmt.setLong(7, client.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
