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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.Matchers.equalTo;

public class JacksonSerializationListToArray {
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
    public void validate_post_request_payload_json_array_as_list() throws JsonProcessingException {
        HashMap<String, String> obj5001 = new HashMap<String, String>();
        obj5001.put("id", "5001");
        obj5001.put("type", "None");

        HashMap<String, String> obj5002 = new HashMap<String, String>();
        obj5002.put("id", "5002");
        obj5002.put("type", "Glazed");

        List<HashMap<String, String>> jsonList = new ArrayList<HashMap<String,String>>();
        jsonList.add(obj5001);
        jsonList.add(obj5002);

        ObjectMapper objectMapper=new ObjectMapper();
        String jsonListStr=objectMapper.writeValueAsString(jsonList);

        given().
                body(jsonListStr).
                when().
                post("/post").
                then().spec(responseSpecification).
                assertThat().
                body("msg", equalTo("Success"));

    }
}
