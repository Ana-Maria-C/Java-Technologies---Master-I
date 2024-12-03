package com.example.laborator8;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import java.util.UUID;

@ApplicationScoped
public class RegistrationNumberGenerator {

    @Produces
    public String generateRegistrationNumber() {
        return UUID.randomUUID().toString();
    }
}