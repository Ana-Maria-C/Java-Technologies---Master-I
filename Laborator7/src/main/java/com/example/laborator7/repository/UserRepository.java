package com.example.laborator7.repository;

import com.example.laborator7.interceptor.Logged;
import com.example.laborator7.model.Student;
import com.example.laborator7.model.Teacher;
import com.example.laborator7.model.User;
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
    public void update(User user) {
        entityManager.merge(user);
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

    public List<Student> getAllStudents() {
        try {
            return entityManager.createQuery("SELECT s FROM Student s", Student.class)
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Teacher> getAllTeachers() {
        try {
            return entityManager.createQuery("SELECT t FROM Teacher t", Teacher.class)
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }


}
