package com.rest.serializationDeserialization;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rest.pojo.assignmentPojo.PojoAssignment1;
import com.rest.pojo.assignmentPojo.PojoAssignment1Address;
import com.rest.pojo.assignmentPojo.PojoAssignment1Geo;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;


public class AssignmentPojo {
    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://jsonplaceholder.typicode.com").
                addHeader("X-Api-Key", "secret").
                setContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(201).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    // Serialize Jackson pojo
    @Test
    public void serialize_json_using_jackson() throws JsonProcessingException {
        PojoAssignment1Geo pojoAssignment1Geo = new PojoAssignment1Geo("-37.3159", "81.1496");
        PojoAssignment1Address pojoAssignment1Address = new PojoAssignment1Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874",pojoAssignment1Geo);
        PojoAssignment1 pojoAssignment1 = new PojoAssignment1("Leanne Graham", "Bret", "Sincere@april.biz",pojoAssignment1Address);


      /*  HashMap<String,Object> geo=new HashMap<String,Object>();
        geo.put("lat","-37.3159");
        geo.put("lng","81.1496");

        HashMap<String,Object> address=new HashMap<String,Object>();
        address.put("street","Kulas Light");
        address.put("suite","Apt. 556");
        address.put("city","Gwenborough");
        address.put("zipcode","92998-3874");
        address.put("geo",geo);

        HashMap<String,Object> mainObject=new HashMap<String,Object>();
        mainObject.put("name","Leanne Graham");
        mainObject.put("username","Bret");
        mainObject.put("email","Sincere@april.biz");
        mainObject.put("address",address);*/


        //    String mainObjectStr = objectMapper.writeValueAsString(mainObject); // convert java object to json object and return it as string


        PojoAssignment1 deserializedPojoAssignment1 = given().
                body(pojoAssignment1).
                when().
                post("/users").
                then().spec(responseSpecification)
                .extract().
                response().
                as(PojoAssignment1.class);
        Assert.assertEquals(deserializedPojoAssignment1.getId(), 11);

/*        given().
                body(mainObjectStr).
                when().
                post("/users").
                then().spec(responseSpecification).
                assertThat().
                body("id",equalTo(11));*/
    }
}
