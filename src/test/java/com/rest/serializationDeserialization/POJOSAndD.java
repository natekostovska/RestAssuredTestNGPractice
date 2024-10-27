package com.rest.serializationDeserialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.pojo.simple.SimplePojo;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.Matchers.equalTo;

public class POJOSAndD {
    /*
     *  POJO stands for Plain Old Java Object. Getters and Setters to be returned for ex if we have workspace class with 2
     * variables name and type (parameters) 1 method, plus we need 4 methods 2 get and 2 set this.name=name; this.type=type;
     * set => this.name=name; get=> return name. We can access the variables only by using the get and set methods private String name, String type
     * Workspace teamWorkspace=new Workspace("MyTeam","team"); // new object
     *
     * Readability, Reusability, Easy access to data, Type Safety, Supports S&D
     * Dis: Unnecessary code, Lombok can help reduce huge amount of code, Can be overkill
     * */
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

    // serialization
    @Test
    public void simple_pojo_example(){

        // SimplePojo simplePojo=new SimplePojo("value1","value2");
        SimplePojo simplePojo=new SimplePojo();
        simplePojo.setKey1("value1");
        simplePojo.setKey2("value2");
        given().
                body(simplePojo).
                when().
                post("/post").
                then().spec(responseSpecification).
                assertThat().
                body("key1", equalTo(simplePojo.getKey1()),"key2",equalTo(simplePojo.getKey2()));
    }

    // deserialization matching full json
    @Test
    public void simple_pojo_example2() throws JsonProcessingException {

        SimplePojo simplePojo=new SimplePojo("value1","value2");
/*        SimplePojo simplePojo=new SimplePojo();
        simplePojo.setKey1("value1");
        simplePojo.setKey2("value2");*/
        SimplePojo deserializedPojo=given().
                body(simplePojo).
                when().
                post("/post").
                then().spec(responseSpecification).
                extract().
                response().
                as(SimplePojo.class);

        ObjectMapper objectMapper=new ObjectMapper();
        String deserializedPojoStr=objectMapper.writeValueAsString(deserializedPojo);
        String simplePojoStr= objectMapper.writeValueAsString(simplePojo);
        Assert.assertEquals(objectMapper.readTree(deserializedPojoStr),objectMapper.readTree(simplePojoStr));
        //asserting entire json object request=response body
    }

}
