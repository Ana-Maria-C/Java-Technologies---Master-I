package com.example.compulsory5.bean;

import com.example.compulsory5.model.Client;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
@Named("clientBean")
public class ClientBean {

    @PersistenceContext(unitName = "MyDataBasePU")
    private EntityManager entityManager;

    private Client currentClient = new Client();
    private Client lastModifiedClient = new Client();
    private LocalDateTime lastModifiedDate;

    public List<Client> getClients() {
        return entityManager.createQuery("SELECT c FROM Client c", Client.class).getResultList();
    }

    public void saveClient() {
        try {
            if (currentClient.getId() == null) {
                entityManager.persist(currentClient);
            } else {
                entityManager.merge(currentClient);
            }
            lastModifiedClient = currentClient;
            lastModifiedDate = LocalDateTime.now();
            currentClient = new Client();
        } catch (Exception e) {
            throw e;
        }
    }

    public Client getCurrentClient() {
        return currentClient;
    }

    public void prepareEdit(Client client) {
        this.currentClient = client;
    }

    public void prepareNewClient() {
        this.currentClient = new Client();
    }

    public Client getLastModifiedClient() {
        return lastModifiedClient;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }
}
