package com.dk.faculty.users.api.v1.resources;

import com.dk.faculty.users.services.beans.StudentBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("student-subjects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentSubjectsResource {
    @Inject
    private StudentBean studentBean;

    @Path("{id}")
    @GET
    public Response getStudentSubjects(@PathParam("id") Integer subjectId) {
        List<Integer> subjectStudents = studentBean.getStudentSubjects(subjectId);
        if(subjectStudents != null) {
            return Response.status(Response.Status.OK).entity(subjectStudents).build();
        }
        throw new BadRequestException("Provided invalid fields. Please check your input.");
    }
}
