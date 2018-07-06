package com.dk.faculty.users.api.v1.resources;

import com.dk.faculty.users.entities.Student;
import com.dk.faculty.users.entities.Subject;
import com.dk.faculty.users.services.beans.StudentBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {
    @Inject
    private StudentBean studentBean;

    @GET
    public Response getStudents() {
        List<Student> students = studentBean.getStudents();
        if(students != null) {
            return Response.status(Response.Status.OK).entity(students).build();
        }
        throw new RuntimeException("Failed getting students.");
    }

    @Path("{id}")
    @GET
    public Response getStudent(@PathParam("id") Integer id) {
        Student student = studentBean.getStudent(id);
        if(student != null) {
            return Response.status(Response.Status.OK).entity(student).build();
        }
        throw new NotFoundException("Student with id " + id + " not found in database.");
    }

    @POST
    public Response addStudent(Student student) {
        Student s = studentBean.addStudent(student);
        if(s != null) {
            return Response.status(Response.Status.CREATED).entity(s).build();
        }
        throw new BadRequestException("Provided invalid fields. Please check your input.");
    }

    @Path("{id}")
    @DELETE
    public Response deleteStudent(@PathParam("id") Integer id) {
        if(studentBean.deleteStudent(id)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        throw new NotFoundException("Student with id " + id + " not found in database.");
    }

    @Path("{id}")
    @PUT
    public Response updateStudent(@PathParam("id") Integer id, Student student) {
        Student s = studentBean.updateStudent(id, student);
        if(s != null) {
            return Response.status(Response.Status.OK).entity(s).build();
        }
        throw new NotFoundException("Student with id " + id + " not found in database.");
    }

    @Path("{id}/subjects")
    @POST
    public Response addSubject(@PathParam("id") Integer id, Subject subject) {
        Student student = studentBean.addSubject(id, subject);
        if(student != null) {
            return Response.status(Response.Status.OK).entity(student).build();
        }
        throw new NotFoundException("Student with id " + id + " not found in database.");
    }

    @Path("{id}/subjects/{sid}")
    @DELETE
    public Response deleteSubject(@PathParam("id") Integer id, @PathParam("sid") Integer subject) {
        Student student = studentBean.removeSubject(id, subject);
        if(student != null) {
            return Response.status(Response.Status.OK).entity(student).build();
        }
        throw new NotFoundException("Student or subject not found in database.");
    }
}
