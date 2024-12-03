package com.example.laborator8.repository;

import com.example.laborator8.RegistrationNumberGenerator;
import com.example.laborator8.model.Evaluation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@ApplicationScoped
public class EvaluationRepository {

    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    RegistrationNumberGenerator registrationNumberGenerator;

    @Transactional
    public void save(Evaluation evaluation) {

        String registrationNumber = registrationNumberGenerator.generateRegistrationNumber();
        evaluation.setRegistration_number(registrationNumber);
        evaluation.setStart_date(LocalDateTime.now());
        evaluation.setEnd_date(LocalDateTime.now().plusMonths(6));
        evaluation.setTimestamp(LocalDateTime.now());
        entityManager.persist(evaluation);

    }

    public Evaluation findById(Long id) {
        return entityManager.find(Evaluation.class, id);
    }

    @Transactional
    public Evaluation update(Long id, Evaluation evaluation) throws Exception {
        Evaluation evaluationExist = entityManager.find(Evaluation.class, id);
        if (evaluationExist == null) {
            throw new Exception("Evaluation not found for ID: " + id);
        }

        evaluationExist.setComment(evaluation.getComment());
        evaluationExist.setGrade(evaluation.getGrade());

        System.out.println("Setting timestamp: " + LocalDateTime.now());
        evaluationExist.setStart_date(LocalDateTime.now());
        evaluationExist.setEnd_date(LocalDateTime.now().plusMonths(6));
        evaluationExist.setTimestamp(LocalDateTime.now());

        evaluationExist.setActivity_type(evaluation.getActivity_type());
        evaluationExist.setSubject_id(evaluation.getSubject_id());
        evaluationExist.setRegistration_number(evaluation.getRegistration_number());
        evaluationExist.setStudent_id(evaluation.getStudent_id());
        evaluationExist.setTeacher_id(evaluation.getTeacher_id());
        evaluationExist.setRegistration_number(evaluation.getRegistration_number());

        return entityManager.merge(evaluationExist);
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

    @Transactional
    public void deleteById(Long id) {
        Evaluation evaluation = entityManager.find(Evaluation.class, id);
        if (evaluation != null) {
            entityManager.remove(evaluation);
        }
    }
}
