package com.rest.requestspecification;

import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class RequestSpecification {
    io.restassured.specification.RequestSpecification requestSpecification;

    @BeforeClass
    public void beforeClass(){
        requestSpecification=with().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "secret").
                log().all();
    }

    @Test
    public void validate_status_code(){
        Response response = requestSpecification.get("/workspaces").then().log().all().extract().response();
        assertThat(response.statusCode(), is(Matchers.equalTo(200)));

    }


    @Test
    public void validate_response_body(){
        Response response = requestSpecification.get("/workspaces").then().log().all().extract().response();
        assertThat(response.statusCode(), is(Matchers.equalTo(200)));
        assertThat(response.path("workspaces[0].name").toString(),equalTo("My Workspace"));
    }


}

// if we don't want to write test cases in bdd style using given, when , then statements it can be done using request specification
// for the given method the return type is request specification but it is an interface, how can a method return object of interface
// java does not allow creation an object of interface it is returning reference of the interface. Interface is a reference type sp we can create variable of type interface

