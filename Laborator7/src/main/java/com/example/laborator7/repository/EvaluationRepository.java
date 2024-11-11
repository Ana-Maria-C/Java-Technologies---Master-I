package com.example.laborator7.repository;

import com.example.laborator7.model.Evaluation;

import com.example.laborator7.model.Subject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@ApplicationScoped
public class EvaluationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Evaluation evaluation) {
        entityManager.persist(evaluation);
    }

    public Evaluation findById(Long id) {
        return entityManager.find(Evaluation.class, id);
    }

    @Transactional
    public Evaluation update(Evaluation evaluation) {
        return entityManager.merge(evaluation);
    }

    @Transactional
    public void delete(Evaluation evaluation) {
        if (entityManager.contains(evaluation)) {
            entityManager.remove(evaluation);
        } else {
            entityManager.remove(entityManager.merge(evaluation));
        }
    }

    public List<Evaluation> findByTeacherId(Long teacherId) {
        return entityManager.createQuery("SELECT e FROM Evaluation e WHERE e.teacher_id = :teacherId", Evaluation.class)
                .setParameter("teacherId", teacherId)
                .getResultList();
    }

    public List<Evaluation> findByStudentId(Long studentId) {
        return entityManager.createQuery("SELECT e FROM Evaluation e WHERE e.student_id = :studentId", Evaluation.class)
                .setParameter("studentId", studentId)
                .getResultList();
    }

    public List<Evaluation> findBySubjectId(Long subjectId) {
        return entityManager.createQuery("SELECT e FROM Evaluation e WHERE e.subject_id = :subjectId", Evaluation.class)
                .setParameter("subjectId", subjectId)
                .getResultList();
    }

    public List<Evaluation> findAll() {
        return entityManager.createQuery("SELECT e FROM Evaluation e", Evaluation.class).getResultList();
    }
}
