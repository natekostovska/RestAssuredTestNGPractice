package com.rest.serializationDeserialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

public class SerializeJacksonArrayNodeToJsonArray {
    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://0b883c06-00fa-45cd-b8e3-1f9d69a05d0d.mock.pstmn.io").
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

    // Serialize List to JSON Array using Jackson
    @Test
    public void serialize_json_array_using_jackson() throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        ArrayNode arrayNodeList=objectMapper.createArrayNode();

        ObjectNode obj5001Node=objectMapper.createObjectNode();
        obj5001Node.put("id", "5001");
        obj5001Node.put("type", "None");

        ObjectNode obj5002Node=objectMapper.createObjectNode();
        obj5002Node.put("id", "5002");
        obj5002Node.put("type", "Glazed");

        arrayNodeList.add(obj5001Node);
        arrayNodeList.add(obj5002Node);

        String jsonListStr=objectMapper.writeValueAsString(arrayNodeList);

        given().
                body(jsonListStr).
                when().
                post("/post").
                then().spec(responseSpecification).
                assertThat().
                body("msg", equalTo("Success"));

    }

}
