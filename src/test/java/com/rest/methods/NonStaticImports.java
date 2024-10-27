package com.rest.methods;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class NonStaticImports {
    // readability is gone
    @Test
    public void simple_test_case(){
        String access_token="secret";
        RestAssured.given().
                baseUri("https://api.postman.com").
                header("x-api-key", access_token).
                when().
                get("/workspaces").
                then().
                statusCode(200).
                body("name", Matchers.is(Matchers.equalTo("Natasha")),
                        "email", Matchers.is(Matchers.equalTo("natashak@gmail.com")));
    }
}
