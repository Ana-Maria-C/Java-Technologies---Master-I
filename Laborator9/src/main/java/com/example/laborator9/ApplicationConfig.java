package com.example.laborator9;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(
                title = "User API",
                version = "1.0.0",
                description = "Protected API for User"

        ),
        servers ={
                @Server(
                        url = "/Laborator9-1.0-SNAPSHOT",
                        description = "Teacher Evaluation REST API Server"
                )}
)

@ApplicationPath("/api")
public class ApplicationConfig extends Application {

}