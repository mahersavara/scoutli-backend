package com.scoutli.api.controller;

import com.scoutli.api.dto.AuthDTO;
import com.scoutli.service.AuthService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {

    @Inject
    AuthService authService;

    @POST
    @Path("/register")
    public Response register(AuthDTO.RegisterRequest request) {
        try {
            authService.register(request);
            return Response.status(201).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/login")
    public Response login(AuthDTO.LoginRequest request) {
        String token = authService.login(request);
        if (token != null) {
            return Response.ok(new AuthDTO.AuthResponse(token)).build();
        }
        return Response.status(401).build();
    }
}
