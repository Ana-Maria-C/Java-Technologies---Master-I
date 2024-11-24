package com.example.laborator7.observers;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class EvaluationObserver {

    public void onEvaluationCreated(@Observes EvaluationCreatedEvent event) {
        System.out.println("New evaluation created with ID: " + event.getEvaluationId());
    }
}
