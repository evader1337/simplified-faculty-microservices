package com.dk.faculty.graphqlaggregator.api;

import com.kumuluz.ee.graphql.GraphQLApplication;
import com.kumuluz.ee.graphql.annotations.GraphQLApplicationClass;

import javax.annotation.security.DeclareRoles;

@GraphQLApplicationClass
@DeclareRoles({"student", "professor", "admin"})
public class Application extends GraphQLApplication {

}
