package com.rest.automateRequests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class AutomateRequests {
    // we did not set content type for get request 'cus we are not sending request body
    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.postman.com").
                addHeader("X-Api-Key", "secret").
                setContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void validate_post_request_bdd_style(){
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"myFirstWorkspace\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"Rest Assured created this\"\n" +
                "    }\n" +
                "}";
        given().
                body(payload).
                when().
                post("/workspaces").
                then().
                log().all().
                assertThat().
                body("workspace.name", equalTo("myFirstWorkspace"),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$")); // https://regex101.com/ to check if it macthesalphanumeric and the length of the string
    }

    @Test
    public void validate_post_request_non_bdd_style(){
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"myFirstWorkspace2\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"Rest Assured created this\"\n" +
                "    }\n" +
                "}";

        Response response = with().
                body(payload).
                post("/workspaces");
        assertThat(response.<String>path("workspace.name"), equalTo("myFirstWorkspace2"));
        assertThat(response.<String>path("workspace.id"), matchesPattern("^[a-z0-9-]{36}$"));
    }

    // PUT REQUEST
    @Test
    public void validate_put_request_bdd_style(){
        String workspaceId = "b61c74b5-9791-4b31-b40d-4a2a66fe2f8c";
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"newWorkspaceName\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"this is created by Rest Assured\"\n" +
                "    }\n" +
                "}";
        given().
                body(payload).
                pathParam("workspaceId", workspaceId).
                when().
                put("/workspaces/{workspaceId}").
                then().
                log().all().
                assertThat().
                body("workspace.name", equalTo("newWorkspaceName"),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"),
                        "workspace.id", equalTo(workspaceId));;
    }

    // DELETE REQUEST
    @Test
    public void validate_delete_request_bdd_style(){
        String workspaceId = "f7fe2565-8f92-4007-b553-a778e06b76e2";
        given().
                pathParam("workspaceId", workspaceId).
                when().
                delete("/workspaces/{workspaceId}").
                then().
                log().all().
                assertThat().
                body("workspace.id", matchesPattern("^[a-z0-9-]{36}$"),
                        "workspace.id", equalTo(workspaceId));;
    }

    @Test
    public void validate_delete_request_non_bdd_style(){
        String workspaceId = "b0fbadbd-7515-4011-9349-32c200525718";
        Response response = with().
                pathParam("workspaceId", workspaceId).
                delete("/workspaces/{workspaceId}");
        assertThat(response.<String>path("workspace.id"), matchesPattern("^[a-z0-9-]{36}$"));
    }

    @Test
    public void validate_post_request_payload_from_file(){
        File file=new File("src/main/resources/CreateWorkspacePayload.json");
        given().
                body(file).
        when().
                post("/workspaces").
        then().
                log().all().
                assertThat().
                body("workspace.name",equalTo("mySecondWorkspace"),"workspace.id",matchesPattern("^[a-z0-9-]{36}$"));
    }

    @Test
    public void validate_post_request_payload_as_map(){
        HashMap<String, Object> mainObject = new HashMap<String,Object>();
        HashMap<String,String> nestedObject=new HashMap<String,String>();
        nestedObject.put("name","myThirdWorkspace");
        nestedObject.put("type","personal");
        nestedObject.put("description","Rest Assured created this");
        mainObject.put("workspace",nestedObject);
        given().
                body(mainObject).
                when().
                post("/workspaces").
                then().
                log().all().
                assertThat().
                body("workspace.name",equalTo("myThirdWorkspace"),"workspace.id",matchesPattern("^[a-z0-9-]{36}$"));
    }
}
