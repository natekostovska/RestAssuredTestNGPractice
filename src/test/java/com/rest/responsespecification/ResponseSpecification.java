package com.rest.responsespecification;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ResponseSpecification {
     @BeforeClass
    public void beforeClass(){
/*        requestSpecification = with().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "secret").
                log().all();*/
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api.postman.com");
        requestSpecBuilder.addHeader("X-Api-Key", "secret");
        requestSpecBuilder.log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

/*        responseSpecification = RestAssured.expect().
                statusCode(200).
                contentType(ContentType.JSON).
                log().all();*/

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void validate_status_code(){
        get("/workspaces");
    }

    @Test
    public void validate_response_body(){
        Response response = get("/workspaces").
                then().
                extract().
                response();
        assertThat(response.path("workspaces[0].name").toString(), equalTo("My Workspace"));
    }
}
