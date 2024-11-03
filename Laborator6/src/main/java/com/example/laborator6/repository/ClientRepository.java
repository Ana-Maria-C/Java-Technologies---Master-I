package com.example.laborator6.repository;

import com.example.laborator6.model.Client;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class ClientRepository {

    @PersistenceContext(unitName = "MyDataBasePU")
    private EntityManager entityManager;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Client create(Client client) {
        entityManager.persist(client);
        return client;
    }

    public Client findById(Long id) {
        return entityManager.find(Client.class, id);
    }
    public Client findByEmail(String email) {
        try {
            return entityManager.createQuery("SELECT c FROM Client c WHERE c.email = :email", Client.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        }
    }

    public List<Client> findAll() {
        return entityManager.createNamedQuery("Client.findAll", Client.class).getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Client update(Client client) {
        return entityManager.merge(client);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(Long id) {
        Client client = findById(id);
        if (client != null) {
            entityManager.remove(client);
        }
    }
}
