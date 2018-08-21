package com.dk.faculty.graphqlaggregator.graphql;

import com.dk.faculty.graphqlaggregator.beans.PlaceBean;
import com.dk.faculty.graphqlaggregator.beans.SubjectBean;
import com.dk.faculty.graphqlaggregator.beans.UserBean;
import com.dk.faculty.graphqlaggregator.entities.Place;
import com.dk.faculty.graphqlaggregator.entities.Professor;
import com.dk.faculty.graphqlaggregator.entities.Student;
import com.dk.faculty.graphqlaggregator.entities.Subject;
import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import com.kumuluz.ee.security.annotations.Secure;
import io.leangen.graphql.annotations.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Secure
@GraphQLClass
public class UsersResolvers {
    @Inject
    private UserBean userBean;

    @Inject
    private SubjectBean subjectBean;

    @Inject
    private PlaceBean placeBean;

    // additional fields
    @GraphQLQuery
    public List<Subject> subjects(@GraphQLContext Student student) {
        List<Subject> subjectList = new ArrayList<Subject>();
        for(Integer subjectId: student.getSubjectIds()) {
            subjectList.add(subjectBean.getSubject(subjectId));
        }
        return subjectList;
    }

    @GraphQLQuery
    public Place cabinet(@GraphQLContext Professor professor) {
        return placeBean.getPlace(professor.getCabinetId());
    }

    // queries
    @RolesAllowed({"professor", "admin"})
    @GraphQLQuery
    public List<Student> getStudents() {
        return userBean.getStudents();
    }

    @RolesAllowed({"professor", "admin"})
    @GraphQLQuery
    public Student getStudent(@GraphQLArgument(name = "id") @GraphQLNonNull Integer id) {
        return userBean.getStudent(id);
    }

    @RolesAllowed({"professor", "admin"})
    @GraphQLQuery
    public List<Professor> getProfessors() {
        return userBean.getProfessors();
    }

    @RolesAllowed({"professor", "admin"})
    @GraphQLQuery
    public Professor getProfessor(@GraphQLArgument(name = "id") @GraphQLNonNull Integer id) {
        return userBean.getProfessor(id);
    }

    @RolesAllowed({"professor", "admin"})
    @GraphQLQuery
    public List<Student> getSubjectStudents(@GraphQLArgument(name = "subjectId") @GraphQLNonNull Integer subjectId) {
        return userBean.getSubjectStudents(subjectId);
    }

    // mutations
    @RolesAllowed("admin")
    @GraphQLMutation
    public Student addStudent(@GraphQLArgument(name = "student") @GraphQLNonNull Student student) {
        return userBean.addStudent(student);
    }

    @RolesAllowed("admin")
    @GraphQLMutation
    public Student editStudent(@GraphQLArgument(name = "id") @GraphQLNonNull Integer id, @GraphQLArgument(name = "student") @GraphQLNonNull Student student) {
        return userBean.editStudent(id, student);
    }

    @RolesAllowed("admin")
    @GraphQLMutation
    public boolean deleteStudent(@GraphQLArgument(name = "id") @GraphQLNonNull Integer id) {
        return userBean.deleteStudent(id);
    }

    @RolesAllowed("student")
    @GraphQLMutation
    public Student enrollStudent(@GraphQLArgument(name = "id") @GraphQLNonNull Integer id, @GraphQLArgument(name = "subjectId") @GraphQLNonNull Integer subjectId) {
        return userBean.enrollStudent(id, subjectId);
    }

    @RolesAllowed("student")
    @GraphQLMutation
    public Student disenrollStudent(@GraphQLArgument(name = "id") @GraphQLNonNull Integer id, @GraphQLArgument(name = "subjectId") @GraphQLNonNull Integer subjectId) {
        return userBean.disenrollStudent(id, subjectId);
    }

    @RolesAllowed("admin")
    @GraphQLMutation
    public Professor addProfessor(@GraphQLArgument(name = "professor") @GraphQLNonNull Professor professor) {
        return userBean.addProfessor(professor);
    }

    @RolesAllowed("admin")
    @GraphQLMutation
    public Professor editProfessor(@GraphQLArgument(name = "id") @GraphQLNonNull Integer id, @GraphQLArgument(name = "professor") @GraphQLNonNull Professor professor) {
        return userBean.editProfessor(id, professor);
    }

    @RolesAllowed("admin")
    @GraphQLMutation
    public boolean deleteProfessor(@GraphQLArgument(name = "id") @GraphQLNonNull Integer id) {
        return userBean.deleteProfessor(id);
    }
}
