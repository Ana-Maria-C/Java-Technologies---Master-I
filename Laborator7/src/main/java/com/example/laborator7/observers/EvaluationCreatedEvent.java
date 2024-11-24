package com.example.laborator7.observers;

public class EvaluationCreatedEvent {
    private final Long evaluationId;

    public EvaluationCreatedEvent(Long evaluationId) {
        this.evaluationId = evaluationId;
    }

    public Long getEvaluationId() {
        return evaluationId;
    }
}
