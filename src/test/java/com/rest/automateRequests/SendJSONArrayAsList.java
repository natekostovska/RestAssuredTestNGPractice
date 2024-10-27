package com.rest.automateRequests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SendJSONArrayAsList {
    ResponseSpecification customResponseSpecification;

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://0b883c06-00fa-45cd-b8e3-1f9d69a05d0d.mock.pstmn.io").
                addHeader("x-mock-match-request-body","true").
                //    setConfig(config.encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).
                //   setContentType(ContentType.JSON).
                //         setConfig(config.encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).
                        setContentType("application/json;charset=utf-8").
                log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        customResponseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void validate_post_request_payload_json_as_array_list(){
//https://opensource.adobe.com/Spry/samples/data_region/JSONDataSetSample.html
        HashMap<String,String> obj5001 = new HashMap<String,String>();
        obj5001.put("id","5001");
        obj5001.put("type","None");

        HashMap<String,String> obj5002 = new HashMap<String,String>();
        obj5002.put("id","5002");
        obj5002.put("type","Glazed");

        List<HashMap<String,String>> jsonList= new ArrayList<HashMap<String,String>>();
        jsonList.add(obj5001);
        jsonList.add(obj5002);

        given().
                body(jsonList).
                when().
                post("/post").
                then().spec(customResponseSpecification).
                assertThat().
                body("msg",equalTo("Success"));
    }

    // content type encoding charset=utf-8
    // just change content type to application/json;charset=utf-8 in postman
    // encoder/decoder section in rest assured page
}
