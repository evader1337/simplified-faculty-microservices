package com.dk.faculty.graphqlaggregator.graphql;

import com.dk.faculty.graphqlaggregator.beans.PlaceBean;
import com.dk.faculty.graphqlaggregator.beans.SubjectBean;
import com.dk.faculty.graphqlaggregator.beans.UserBean;
import com.dk.faculty.graphqlaggregator.entities.Place;
import com.dk.faculty.graphqlaggregator.entities.Professor;
import com.dk.faculty.graphqlaggregator.entities.Student;
import com.dk.faculty.graphqlaggregator.entities.Subject;
import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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
    @GraphQLQuery
    public List<Student> getStudents() {
        return userBean.getStudents();
    }

    @GraphQLQuery
    public Student getStudent(@GraphQLArgument(name = "id") @GraphQLNonNull Integer id) {
        return userBean.getStudent(id);
    }

    @GraphQLQuery
    public List<Professor> getProfessors() {
        return userBean.getProfessors();
    }

    @GraphQLQuery
    public Professor getProfessor(@GraphQLArgument(name = "id") @GraphQLNonNull Integer id) {
        return userBean.getProfessor(id);
    }
}
