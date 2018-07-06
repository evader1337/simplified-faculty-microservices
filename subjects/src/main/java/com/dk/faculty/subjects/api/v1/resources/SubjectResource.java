package com.dk.faculty.subjects.api.v1.resources;

import com.dk.faculty.subjects.entities.Subject;
import com.dk.faculty.subjects.services.beans.SubjectBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("subjects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubjectResource {
    @Inject
    private SubjectBean subjectBean;

    @GET
    public Response getSubjects() {
        List<Subject> students = subjectBean.getSubjects();
        if(students != null) {
            return Response.status(Response.Status.OK).entity(students).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("{id}")
    @GET
    public Response getSubject(@PathParam("id") Integer id) {
        Subject subject = subjectBean.getSubject(id, true);
        if(subject != null) {
            return Response.status(Response.Status.OK).entity(subject).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response addSubject(Subject subject) {
        Subject s = subjectBean.addSubject(subject);
        if(s != null) {
            return Response.status(Response.Status.CREATED).entity(s).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Path("{id}")
    @DELETE
    public Response deleteSubject(@PathParam("id") Integer id) {
        if(subjectBean.deleteSubject(id)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("{id}")
    @PUT
    public Response updateSubject(@PathParam("id") Integer id, Subject subject) {
        Subject s = subjectBean.updateSubject(id, subject);
        if(s != null) {
            return Response.status(Response.Status.OK).entity(s).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
