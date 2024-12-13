package com.example.laborator9.repository;

import com.example.laborator9.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class UserRepository implements Serializable {

    @PersistenceContext(unitName = "MyDataBasePU")
    private EntityManager entityManager;

    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }

    @Transactional
    public User update(Long id, User user) throws Exception {
        User userExist = entityManager.find(User.class, id);
        if (userExist == null){
            throw new Exception("User not found for ID: " +id);
        }
        userExist.setEmail(user.getEmail());
        userExist.setName(user.getEmail());
        userExist.setPassword(user.getPassword());
        return entityManager.merge(userExist);
    }

    @Transactional
    public void delete(User user) {
        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
    }

    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public User findByEmail(String email) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public User findByName (String name){
        try{
            return entityManager.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class)
                    .setParameter("name", name)
                    .getSingleResult();
        }catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public void deleteById(Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

}
