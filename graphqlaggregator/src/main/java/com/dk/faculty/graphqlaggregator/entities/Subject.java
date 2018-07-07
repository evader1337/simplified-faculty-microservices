package com.dk.faculty.graphqlaggregator.entities;

import io.leangen.graphql.annotations.GraphQLIgnore;
import io.leangen.graphql.annotations.GraphQLNonNull;

import java.util.List;

public class Subject {
    private Integer id;
    private List<Integer> studentIds;
    private String title;
    private Integer placeId;
    private Integer professorId;

    public Integer getId() {
        return id;
    }

    @GraphQLIgnore
    public void setId(Integer id) {
        this.id = id;
    }

    @GraphQLIgnore
    public List<Integer> getStudentIds() {
        return studentIds;
    }

    @GraphQLIgnore
    public void setStudentIds(List<Integer> studentIds) {
        this.studentIds = studentIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @GraphQLIgnore
    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    @GraphQLIgnore
    public Integer getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Integer professorId) {
        this.professorId = professorId;
    }
}
