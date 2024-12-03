package com.example.laborator8;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(
                title = "Evaluation API",
                version = "1.0.0",
                description = "REST API for Evaluation"
        ),
        servers = {
                @Server(
                        url = "/Laborator8-1.0-SNAPSHOT",
                        description = "Teacher Evaluation REST API Server"
                )
        }
)
@ApplicationPath("/api")
public class ApplicationConfig extends Application {

}