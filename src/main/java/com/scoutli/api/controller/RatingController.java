package com.scoutli.api.controller;

import com.scoutli.api.dto.RatingDTO;
import com.scoutli.service.RatingService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/api/discoveries/{discoveryId}/ratings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RatingController {

    @Inject
    RatingService ratingService;

    @POST
    @RolesAllowed({ "MEMBER", "ADMIN" })
    public Response create(@PathParam("discoveryId") Long discoveryId, RatingDTO.CreateRequest request,
            @Context SecurityContext securityContext) {
        String email = securityContext.getUserPrincipal().getName();
        RatingDTO created = ratingService.createOrUpdateRating(discoveryId, request, email);
        return Response.ok(created).build();
    }
}
