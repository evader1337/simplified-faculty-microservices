package com.dk.faculty.users.api.v1.resources;

import com.dk.faculty.users.entities.Professor;
import com.dk.faculty.users.services.beans.ProfessorBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("professors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfessorResource {
    @Inject
    private ProfessorBean professorBean;

    @GET
    public Response getProfessors() {
        List<Professor> professors = professorBean.getProfessors();
        if(professors != null) {
            return Response.status(Response.Status.OK).entity(professors).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("{id}")
    @GET
    public Response getProfessor(@PathParam("id") Integer id) {
        Professor professor = professorBean.getProfessor(id);
        if(professor != null) {
            return Response.status(Response.Status.OK).entity(professor).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response addProfessor(Professor professor) {
        Professor p = professorBean.addProfessor(professor);
        if(p != null) {
            return Response.status(Response.Status.CREATED).entity(p).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Path("{id}")
    @DELETE
    public Response deleteProfessor(@PathParam("id") Integer id) {
        if(professorBean.deleteProfessor(id)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Path("{id}")
    @PUT
    public Response updateProfessor(@PathParam("id") Integer id, Professor professor) {
        Professor p = professorBean.updateProfessor(id, professor);
        if(p != null) {
            return Response.status(Response.Status.OK).entity(p).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
