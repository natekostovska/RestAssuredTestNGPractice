package com.rest.methods;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class StaticImports {
    //readability is important usage of this static import
    @org.testng.annotations.Test
    public void simple_test_case(){
        String access_token="secret";
        given().
                baseUri("https://api.postman.com").
                header("x-api-key", access_token).
                when().
                get("/workspaces").
                then().
                statusCode(200).
                body("name", is(equalTo("Natasha")),
                        "email", is(equalTo("natashak@gmail.com")));
    }
}
