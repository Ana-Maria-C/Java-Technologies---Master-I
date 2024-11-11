package com.example.laborator7.repository;

import com.example.laborator7.model.Subject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class SubjectRepository {

    @PersistenceContext(unitName = "MyDataBasePU")
    private EntityManager entityManager;

    @Transactional
    public void save(Subject subject) {
        entityManager.persist(subject);
    }

    @Transactional
    public void update(Subject subject) {
        entityManager.merge(subject);
    }

    @Transactional
    public void delete(Subject subject) {
        entityManager.remove(entityManager.contains(subject) ? subject : entityManager.merge(subject));
    }

    public Subject findById(Long id) {
        return entityManager.find(Subject.class, id);
    }

    public List<Subject> findAll() {
        return entityManager.createQuery("SELECT s FROM Subject s", Subject.class).getResultList();
    }

    public Subject findByName(String name) {
        try {
            return entityManager.createQuery("SELECT s FROM Subject s WHERE s.name = :name", Subject.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Subject> findSubjectsByTeacherId(Long teacherId) {
        try {
            return entityManager.createQuery(
                            "SELECT s FROM Subject s JOIN s.teachers t WHERE t.id = :teacherId",
                            Subject.class)
                    .setParameter("teacherId", teacherId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
