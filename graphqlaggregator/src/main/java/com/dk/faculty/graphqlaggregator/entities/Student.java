package com.dk.faculty.graphqlaggregator.entities;

import io.leangen.graphql.annotations.GraphQLIgnore;

import java.util.List;

public class Student extends User {
    private List<Integer> subjectIds;

    @GraphQLIgnore
    public List<Integer> getSubjectIds() {
        return subjectIds;
    }

    @GraphQLIgnore
    public void setSubjectIds(List<Integer> subjectIds) {
        this.subjectIds = subjectIds;
    }
}
