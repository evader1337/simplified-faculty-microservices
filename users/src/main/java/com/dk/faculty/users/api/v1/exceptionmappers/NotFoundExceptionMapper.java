package com.dk.faculty.users.api.v1.exceptionmappers;

import com.dk.faculty.users.entities.Error;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    public Response toResponse(NotFoundException e) {
        Error error = new Error();
        error.setCode(404);
        error.setDescription(e.getMessage());
        error.setLocation("Microservice: users");
        return Response.status(Response.Status.NOT_FOUND).entity(error).build();
    }
}
