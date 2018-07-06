package com.dk.faculty.users.api.v1.exceptionmappers;

import com.dk.faculty.users.entities.Error;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {
    public Response toResponse(BadRequestException e) {
        Error error = new Error();
        error.setCode(400);
        error.setDescription(e.getMessage());
        error.setLocation("Microservice: users");
        return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
    }
}
