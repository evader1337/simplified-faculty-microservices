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

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Secure
@GraphQLClass
public class SubjectResolvers {
    @Inject
    private SubjectBean subjectBean;

    @Inject
    private PlaceBean placeBean;

    @Inject
    private UserBean userBean;

    // additional fields
    @GraphQLQuery
    public Place place(@GraphQLContext Subject subject) {
        return placeBean.getPlace(subject.getPlaceId());
    }

    @GraphQLQuery
    public Professor professor(@GraphQLContext Subject subject) {
        return userBean.getProfessor(subject.getProfessorId());
    }

    @GraphQLQuery
    public List<Student> students(@GraphQLContext Subject subject) {
        List<Student> studentList = new ArrayList<Student>();
        for(Integer id: subject.getStudentIds()) {
            studentList.add(userBean.getStudent(id));
        }
        return studentList;
    }

    // queries
    @PermitAll
    @GraphQLQuery
    public List<Subject> getSubjects() {
        return subjectBean.getSubjects();
    }

    @PermitAll
    @GraphQLQuery
    public Subject getSubject(@GraphQLArgument(name = "id") @GraphQLNonNull Integer id) {
        return subjectBean.getSubject(id);
    }

    // mutations
    @RolesAllowed({"professor", "admin"})
    @GraphQLMutation
    public Subject addSubject(@GraphQLArgument(name = "subject") @GraphQLNonNull Subject subject) {
        return subjectBean.addSubject(subject);
    }

    @RolesAllowed({"professor", "admin"})
    @GraphQLMutation
    public Subject editSubject(@GraphQLArgument(name = "id") @GraphQLNonNull Integer id, @GraphQLArgument(name = "subject") @GraphQLNonNull Subject subject) {
        return subjectBean.editSubject(id, subject);
    }

    @RolesAllowed({"professor", "admin"})
    @GraphQLMutation
    public boolean deleteSubject(@GraphQLArgument(name = "id") @GraphQLNonNull Integer id) {
        return subjectBean.deleteSubject(id);
    }
}
