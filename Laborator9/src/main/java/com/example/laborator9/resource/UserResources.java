package com.example.laborator9.resource;


import com.example.laborator9.model.User;
import com.example.laborator9.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name="Users", description = " Rest API for Users")
public class UserResources {

    @Inject
    UserRepository userRepository;

    @GET
    @RolesAllowed("admin")
    @Operation(summary = "List all user entries",
            description = "Retrieves a list of all user entries",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of user entries")
            })
    public Response getAllUsers() {
        List<User> users = userRepository.findAll();
        return Response.ok(users).build();
    }

    @GET
    @RolesAllowed("admin")
    @Path("/{id}")
    @Operation(
            summary = "List a specific user",
            description = "Retrieves a specific user entries for the given user ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the user entry"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    public Response getUserById(@Parameter(description = "User ID to retrieve user entry", required = true) @PathParam("id") Long id) {
        User userExist = userRepository.findById(id);
        if (userExist == null )
        {
            return Response.status(Response.Status.NOT_FOUND).entity("No user found with ID: " + id).build();
        }
        return Response.ok(userExist).build();
    }


    @GET
    @RolesAllowed("admin")
    @Path("/email/{email}")
    @Operation(
            summary = "List a specific user",
            description = "Retrieves a specific user entries for the given user email",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the user entry"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    public Response getUserByEmail(@Parameter(description = "User Email to retrieve user entry", required = true) @PathParam("email") String email) {
        User userExist = userRepository.findByEmail(email);
        if (userExist == null )
        {
            return Response.status(Response.Status.NOT_FOUND).entity("No user found with email: " + email).build();
        }
        return Response.ok(userExist).build();
    }


    @POST
    @RolesAllowed({"admin", "user"})
    @Operation(summary = "Create a new user", description = "Submits a new user entry and stores it in the database",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successfully created the user entry"),
                    @ApiResponse(responseCode = "400", description = "Invalid input provided")
            })
    public Response addUser(@Parameter(description = "User object to be created", required = true)User user) {
        try {
            userRepository.save(user);
            return Response.status(Response.Status.CREATED).entity(user).build();
        }
        catch(Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }


    @PUT
    @RolesAllowed("admin")
    @Path("/{id}")
    @Operation(summary = "Update an existing user entry",
            description = "Updates an existing user entry specified by its ID with new data",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Successfully updated the user entry"),
                    @ApiResponse(responseCode = "404", description = "User entry not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid input provided")
            })
    public Response updateUser(@Parameter(description = "ID of the user entry to be updated", required = true) @PathParam("id") Long id,
                                     @Parameter(description = "Updated user object", required = true)User newUser) {
        try {
            userRepository.update(id, newUser);

            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @RolesAllowed("admin")
    @Path("/{id}")
    @Operation(summary = "Delete an user entry",
            description = "Deletes the user entry specified by its ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Successfully deleted the user entry"),
                    @ApiResponse(responseCode = "404", description = "User entry not found")
            })
    public Response deleteUser(@Parameter(description = "ID of the user entry to be deleted", required = true) @PathParam("id") Long id){
        try {
            userRepository.deleteById(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

}
