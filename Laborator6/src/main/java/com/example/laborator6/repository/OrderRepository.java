package com.example.laborator6.repository;

import com.example.laborator6.model.Client;
import com.example.laborator6.model.Order;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Stateless
public class OrderRepository {

    @PersistenceContext(unitName = "MyDataBasePU")
    private EntityManager entityManager;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Order create(Order order) {
        entityManager.persist(order);
        return order;
    }

    public Order findById(Long id) {
        return entityManager.find(Order.class, id);
    }

    public List<Order> findAll() {
        TypedQuery<Order> query = entityManager.createQuery("SELECT o FROM Order o", Order.class);
        return query.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Order update(Order order) {
        return entityManager.merge(order);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(Long id) {
        Order order = findById(id);
        if (order != null) {
            entityManager.remove(order);
        }
    }

    public List<Order> findByClient(Client client) {
        TypedQuery<Order> query = entityManager.createQuery(
                "SELECT o FROM Order o WHERE o.client = :client", Order.class);
        query.setParameter("client", client);
        return query.getResultList();
    }
}
