package com.example.laborator8.resource;

import com.example.laborator8.model.Evaluation;
import com.example.laborator8.repository.EvaluationRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/evaluations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name="Evaluations", description = " Rest API for Evaluation")
public class EvaluationResource {
    @Inject
    private EvaluationRepository repository;


    @GET
    @Operation(summary = "List all evaluation entries",
            description = "Retrieves a list of all evaluation entries",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of evaluation entries")
            })
    public Response getAllEvaluations() {
        List <Evaluation> evaluations = repository.findAll();
        return Response.ok(evaluations).build();
    }

    @GET
    @Path("/{id}")
    @Operation(
            summary = "List a specific evaluation",
            description = "Retrieves a specific evaluation entries for the given evaluation ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the evaluation entry"),
                    @ApiResponse(responseCode = "404", description = "Evaluation not found")
            }
    )
    public Response getEvaluationById(@Parameter(description = "Evaluation ID to retrieve evaluation entry", required = true) @PathParam("id") Long id) {
        Evaluation evaluationExist = repository.findById(id);
        if (evaluationExist == null )
        {
            return Response.status(Response.Status.NOT_FOUND).entity("No evaluation found with ID: " + id).build();
        }
        return Response.ok(evaluationExist).build();
    }

    @GET
    @Path("/student/{id}")
    @Operation(
            summary = "List all evaluation entries for a specific student",
            description = "Retrieves a list of all timetable entries for the given student ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the evaluation entry"),
                    @ApiResponse(responseCode = "404", description = "User not found or no evaluations for the user")
            }
    )
    public Response getEvaluationsByStudentId(@Parameter(description = "Student ID to retrieve evaluations for", required = true) @PathParam("id") Long userId) {
        List<Evaluation> evaluations = repository.findByStudentId(userId);
        if (evaluations == null || evaluations.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No evaluations found for user with ID: " + userId).build();
        }
        return Response.ok(evaluations).build();
    }

    @GET
    @Path("/teacher/{id}")
    @Operation(
            summary = "List all evaluation entries for a specific teacher",
            description = "Retrieves a list of all timetable entries for the given teacher ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the evaluation entry"),
                    @ApiResponse(responseCode = "404", description = "User not found or no evaluations for the user")
            }
    )
    public Response getEvaluationsByTeacherId(@Parameter(description = "Teacher ID to retrieve evaluations for", required = true) @PathParam("id") Long userId) {
        List<Evaluation> evaluations = repository.findByTeacherId(userId);
        if (evaluations == null || evaluations.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No evaluations found for user with ID: " + userId).build();
        }
        return Response.ok(evaluations).build();
    }


    @POST
    @Operation(summary = "Create a new evaluation", description = "Submits a new evaluation entry and stores it in the database",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successfully created the evaluation entry"),
                    @ApiResponse(responseCode = "400", description = "Invalid input provided")
            })
    public Response addEvaluation(@Parameter(description = "Evaluation object to be created", required = true)Evaluation evaluation) {
        try {
            repository.save(evaluation);
            return Response.status(Response.Status.CREATED).entity(evaluation).build();
        }
        catch(Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update an existing evaluation entry",
            description = "Updates an existing evaluation entry specified by its ID with new data",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Successfully updated the evaluatiom entry"),
                    @ApiResponse(responseCode = "404", description = "Evaluation entry not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid input provided")
            })
    public Response updateEvaluation(@Parameter(description = "ID of the evaluation entry to be updated", required = true) @PathParam("id") Long id,
                                     @Parameter(description = "Updated evaluation object", required = true)Evaluation newEvaluation) {
        try {
            repository.update(id, newEvaluation);

            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a evaluation entry",
            description = "Deletes the evaluation entry specified by its ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Successfully deleted the evaluation entry"),
                    @ApiResponse(responseCode = "404", description = "Evaluation entry not found")
            })
    public Response deleteEvaluation(@Parameter(description = "ID of the evaluation entry to be deleted", required = true) @PathParam("id") Long id){
        try {
            repository.deleteById(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
