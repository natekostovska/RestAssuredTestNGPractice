package com.rest.serializationDeserialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class TypesOfObjectMappingSerializeMapToJsonObjectJackson {
    /*
    Supported by Rest Assured
    https://github.com/rest-assured/rest-assured/wiki/Usage#object-mapping
    whatever library we should use should be added in the classpath, it should be added as dependency

    * */

    // Serialize Map to JSON Object using Jackson

    // we did not set content type for get request 'cus we are not sending request body
    @BeforeClass
    public void beforeClass() {
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
    public void validate_post_request_payload_as_map() throws JsonProcessingException {
        HashMap<String, Object> mainObject = new HashMap<String, Object>();

        HashMap<String, String> nestedObject = new HashMap<String, String>();
        nestedObject.put("name", "myWorkspace1");
        nestedObject.put("type", "personal");
        nestedObject.put("description", "Rest Assured created this");

        mainObject.put("workspace", nestedObject);

        ObjectMapper objectMapper = new ObjectMapper();
        String mainObjectStr = objectMapper.writeValueAsString(mainObject); // convert java object to json object and return it as string

        given().
                body(mainObjectStr).
                when().
                post("/workspaces").
                then().spec(responseSpecification)
                .assertThat().
                body("workspace.name", equalTo("myWorkspace1"),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"));
    }

}
