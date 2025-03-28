package com.example.compulsory.bean;

import com.example.compulsory.model.Client;
import jakarta.annotation.Resource;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name="clientBean")
@SessionScoped
public class ClientBean {

    @Resource(name = "jdbc/PostgresPool")
    private DataSource ds;
    private List<Client> clients ;
    private Client currentClient = new Client();
    public List<Client> getClients() {

        clients = new ArrayList<>() ;

        String sql = "SELECT id, name, email, phone, address, dateOfBirth, gender FROM clients";

        try (Connection conn = ds.getConnection();
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

    public void prepareEdit(Client client){
        this.currentClient = client;
    }

    public void prepareNewClient() {
        this.currentClient = new Client();
    }

    public void saveClient() {
        if (currentClient.getId() == null) {
            String sql = "INSERT INTO clients (name, email, phone, address, dateOfBirth, gender) VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection conn = ds.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, currentClient.getName());
                stmt.setString(2, currentClient.getEmail());
                stmt.setString(3, currentClient.getPhone());
                stmt.setString(4, currentClient.getAddress());
                stmt.setDate(5, new Date(currentClient.getDateOfBirth().getTime()));
                stmt.setString(6, currentClient.getGender());
                stmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            String sql = "UPDATE clients SET name = ?, email = ?, phone = ?, address = ?, dateOfBirth = ?, gender = ?  WHERE id = ?";

            try (Connection conn = ds.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, currentClient.getName());
                stmt.setString(2, currentClient.getEmail());
                stmt.setString(3, currentClient.getPhone());
                stmt.setString(4, currentClient.getAddress());
                stmt.setDate(5, new Date(currentClient.getDateOfBirth().getTime()));
                stmt.setString(6, currentClient.getGender());
                stmt.setLong(7, currentClient.getId());
                stmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // refresh client list and reset current client
        clients = getClients();
        this.currentClient = new Client();
    }

    public Client getCurrentClient() {
        return currentClient;
    }

    public void setCurrentClient(Client currentClient) {
        this.currentClient = currentClient;
    }

}
