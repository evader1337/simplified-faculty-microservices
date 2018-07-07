package com.dk.faculty.graphqlaggregator.beans;

import com.dk.faculty.graphqlaggregator.entities.Error;
import com.dk.faculty.graphqlaggregator.entities.Professor;
import com.dk.faculty.graphqlaggregator.entities.Student;
import com.dk.faculty.graphqlaggregator.entities.Subject;
import com.kumuluz.ee.discovery.utils.DiscoveryUtil;
import graphql.GraphQLException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserBean {
    private Client httpClient;
    private String baseUrlUsers;

    @Inject
    DiscoveryUtil discoveryUtil;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
        baseUrlUsers = discoveryUtil.getServiceInstance("users", "1.0.0", "dev").get().toString() + "/v1";
    }

    public List<Student> getSubjectStudents(Integer subjectId) {
        Response r = httpClient
                .target(baseUrlUsers)
                .path("subject-students")
                .path("{id}")
                .resolveTemplate("id", subjectId)
                .request()
                .get();
        if(r.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()) {
            List<Integer> studentIds = r.readEntity(new GenericType<List<Integer>>(){});
            List<Student> students = new ArrayList<Student>();
            for(Integer id: studentIds) {
                students.add(getStudent(id));
            }
            return students;
        } else {
            Error e = r.readEntity(Error.class);
            throw new GraphQLException(e.getDescription() + " " + e.getLocation());
        }
    }

    public List<Student> getStudents() {
        Response r = httpClient
                .target(baseUrlUsers)
                .path("students")
                .request()
                .get();
        if (r.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()) {
            return r.readEntity(new GenericType<List<Student>>(){});
        } else {
            Error e = r.readEntity(Error.class);
            throw new GraphQLException(e.getDescription() + " " + e.getLocation());
        }
    }

    public Student getStudent(Integer id) {
        Response r = httpClient
                .target(baseUrlUsers)
                .path("students")
                .path("{id}")
                .resolveTemplate("id", id)
                .request()
                .get();
        if (r.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()) {
            return r.readEntity(Student.class);
        } else {
            Error e = r.readEntity(Error.class);
            throw new GraphQLException(e.getDescription() + " " + e.getLocation());
        }
    }

    public Student addStudent(Student student) {
        Response r = httpClient
                .target(baseUrlUsers)
                .path("students")
                .request()
                .post(Entity.json(student));
        if(r.getStatusInfo().getStatusCode() == Response.Status.CREATED.getStatusCode()) {
            return r.readEntity(Student.class);
        } else {
            Error e = r.readEntity(Error.class);
            throw new GraphQLException(e.getDescription() + " " + e.getLocation());
        }
    }

    public Student editStudent(Integer id, Student student) {
        Response r = httpClient
                .target(baseUrlUsers)
                .path("students")
                .path("{id}")
                .resolveTemplate("id", id)
                .request()
                .put(Entity.json(student));
        if(r.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()) {
            return r.readEntity(Student.class);
        } else {
            Error e = r.readEntity(new GenericType<Error>(){});
            throw new GraphQLException(e.getDescription() + " " + e.getLocation());
        }
    }

    public boolean deleteStudent(Integer id) {
        Response r = httpClient
                .target(baseUrlUsers)
                .path("students")
                .path("{id}")
                .resolveTemplate("id", id)
                .request()
                .delete();
        if(r.getStatusInfo().getStatusCode() == Response.Status.NO_CONTENT.getStatusCode()) {
            return true;
        } else {
            Error e = r.readEntity(new GenericType<Error>(){});
            throw new GraphQLException(e.getDescription() + " " + e.getLocation());
        }
    }

    public Student enrollStudent(Integer id, Integer subjectId) {
        class TestInput {
            private Integer subjectId;

            public TestInput(Integer subjectId) {
                this.subjectId = subjectId;
            }

            public Integer getSubjectId() {
                return subjectId;
            }

            public void setSubjectId(Integer subjectId) {
                this.subjectId = subjectId;
            }
        }
        Response r = httpClient
                .target(baseUrlUsers)
                .path("students")
                .path("{id}")
                .path("subjects")
                .resolveTemplate("id", id)
                .request()
                .post(Entity.json(new TestInput(subjectId)));
        if(r.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()) {
            return r.readEntity(Student.class);
        } else {
            Error e = r.readEntity(new GenericType<Error>(){});
            throw new GraphQLException(e.getDescription() + " " + e.getLocation());
        }
    }

    public Student disenrollStudent(Integer id, Integer subjectId) {
        Response r = httpClient
                .target(baseUrlUsers)
                .path("students")
                .path("{id}")
                .path("subjects")
                .path("{sid}")
                .resolveTemplate("id", id)
                .resolveTemplate("sid", subjectId)
                .request()
                .delete();
        if(r.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()) {
            return r.readEntity(Student.class);
        } else {
            Error e = r.readEntity(new GenericType<Error>(){});
            throw new GraphQLException(e.getDescription() + " " + e.getLocation());
        }
    }

    public List<Professor> getProfessors() {
        Response r = httpClient
                .target(baseUrlUsers)
                .path("professors")
                .request()
                .get();
        if (r.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()) {
            return r.readEntity(new GenericType<List<Professor>>(){});
        } else {
            Error e = r.readEntity(Error.class);
            throw new GraphQLException(e.getDescription() + " " + e.getLocation());
        }
    }

    public Professor getProfessor(Integer id) {
        Response r = httpClient
                .target(baseUrlUsers)
                .path("professors")
                .path("{id}")
                .resolveTemplate("id", id)
                .request()
                .get();
        if (r.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()) {
            return r.readEntity(Professor.class);
        } else {
            Error e = r.readEntity(Error.class);
            throw new GraphQLException(e.getDescription() + " " + e.getLocation());
        }
    }

    public Professor addProfessor(Professor professor) {
        Response r = httpClient
                .target(baseUrlUsers)
                .path("professors")
                .request()
                .post(Entity.json(professor));
        if(r.getStatusInfo().getStatusCode() == Response.Status.CREATED.getStatusCode()) {
            return r.readEntity(Professor.class);
        } else {
            Error e = r.readEntity(new GenericType<Error>(){});
            throw new GraphQLException(e.getDescription() + " " + e.getLocation());
        }
    }

    public Professor editProfessor(Integer id, Professor professor) {
        Response r = httpClient
                .target(baseUrlUsers)
                .path("professors")
                .path("{id}")
                .resolveTemplate("id", id)
                .request()
                .put(Entity.json(professor));
        if(r.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()) {
            return r.readEntity(Professor.class);
        } else {
            Error e = r.readEntity(new GenericType<Error>(){});
            throw new GraphQLException(e.getDescription() + " " + e.getLocation());
        }
    }

    public boolean deleteProfessor(Integer id) {
        Response r = httpClient
                .target(baseUrlUsers)
                .path("professors")
                .path("{id}")
                .resolveTemplate("id", id)
                .request()
                .delete();
        if(r.getStatusInfo().getStatusCode() == Response.Status.NO_CONTENT.getStatusCode()) {
            return true;
        } else {
            Error e = r.readEntity(new GenericType<Error>(){});
            throw new GraphQLException(e.getDescription() + " " + e.getLocation());
        }
    }
}
