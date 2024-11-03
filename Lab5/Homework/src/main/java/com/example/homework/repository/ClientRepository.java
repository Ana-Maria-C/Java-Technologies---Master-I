package com.example.homework.repository;

import com.example.homework.model.Client;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
public class ClientRepository {

    @PersistenceContext(unitName = "MyDataBasePU")
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Client create(Client client) {
        entityManager.persist(client);
        return client;
    }

    public Client findById(Long id) {
        return entityManager.find(Client.class, id);
    }

    public List<Client> findAll() {
        return entityManager.createNamedQuery("Client.findAll", Client.class).getResultList();
    }

    @Transactional
    public Client update(Client client) {
        return entityManager.merge(client);
    }

    @Transactional
    public void delete(Long id) {
        Client client = findById(id);
        if (client != null) {
            entityManager.remove(client);
        }
    }
}
