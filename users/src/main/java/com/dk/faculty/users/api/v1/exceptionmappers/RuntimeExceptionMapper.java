package com.dk.faculty.users.api.v1.exceptionmappers;

import com.dk.faculty.users.entities.Error;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {
    public Response toResponse(RuntimeException e) {
        Error error = new Error();
        error.setCode(500);
        error.setDescription(e.getMessage());
        error.setLocation("Microservice: users");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
    }
}
