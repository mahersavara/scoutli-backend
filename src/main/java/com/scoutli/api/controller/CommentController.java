package com.scoutli.api.controller;

import com.scoutli.api.dto.CommentDTO;
import com.scoutli.service.CommentService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/api/discoveries/{discoveryId}/comments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentController {

    @Inject
    CommentService commentService;

    @GET
    public List<CommentDTO> list(@PathParam("discoveryId") Long discoveryId) {
        return commentService.getCommentsByDiscoveryId(discoveryId);
    }

    @POST
    @RolesAllowed({ "MEMBER", "ADMIN" })
    public Response create(@PathParam("discoveryId") Long discoveryId, CommentDTO.CreateRequest request,
            @Context SecurityContext securityContext) {
        String email = securityContext.getUserPrincipal().getName();
        CommentDTO created = commentService.createComment(discoveryId, request, email);
        return Response.status(201).entity(created).build();
    }
}
