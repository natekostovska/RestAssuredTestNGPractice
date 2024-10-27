package com.rest.serializationDeserialization;

import com.fasterxml.jackson.databind.node.ObjectNode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class SerializeJacksonObjectNodeToJSONObject {
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

    // Serialize Jackson object node to json object
    @Test
    public void serialize_json_using_jackson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode nestedObjectNode = objectMapper.createObjectNode();
        nestedObjectNode.put("name", "myWorkspace3");
        nestedObjectNode.put("type", "personal");
        nestedObjectNode.put("description", "Rest Assured created this");

        ObjectNode mainObjectNode = objectMapper.createObjectNode();
        mainObjectNode.set("workspace", nestedObjectNode);

        String mainObjectStr = objectMapper.writeValueAsString(mainObjectNode);

        // convert java object to json object and return it as string instead of using hash map

        given().
                body(mainObjectNode). // we can also add mainObjectStr
                when().
                post("/workspaces").
                then().spec(responseSpecification)
                .assertThat().
                body("workspace.name", equalTo("myWorkspace3"),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"));
    }
}
