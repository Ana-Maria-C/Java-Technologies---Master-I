package com.example.laborator7.decorator;

import com.example.laborator7.model.Evaluation;
import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.inject.Inject;
import java.time.LocalDateTime;

@Decorator
public class EvaluationServiceDecorator implements EvaluationService {

    @Inject
    @Delegate
    private EvaluationService evaluationService;

    private static final LocalDateTime START_DATE = LocalDateTime.of(2024, 11, 10, 14, 0);
    private static final LocalDateTime END_DATE = LocalDateTime.of(2024, 11, 25, 23, 59);

    @Override
    public void createEvaluation(Evaluation evaluation) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(START_DATE) || now.isAfter(END_DATE)) {
            throw new IllegalStateException("Evaluations can only be submitted within the allowed time range.");
        }
        evaluationService.createEvaluation(evaluation);
    }
}
