package com.example.laborator7.decorator;

import com.example.laborator7.model.Evaluation;
import com.example.laborator7.repository.EvaluationRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EvaluationServiceImpl implements EvaluationService {

    @Inject
    private EvaluationRepository evaluationRepository;

    @Override
    public void createEvaluation(Evaluation evaluation) {
        evaluationRepository.save(evaluation);
    }
}
