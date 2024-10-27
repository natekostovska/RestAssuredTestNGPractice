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

public class ComplexJsonAsRequestPayload {
    ResponseSpecification customResponseSpecification;

    @BeforeClass
    public void beforeClass() {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://0b883c06-00fa-45cd-b8e3-1f9d69a05d0d.mock.pstmn.io").
                addHeader("x-mock-match-request-body","true").
                setContentType("application/json;charset=utf-8").
                log(LogDetail.ALL);

        RestAssured.requestSpecification =requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        customResponseSpecification = responseSpecBuilder.build();

    }

    @Test
    public void validate_post_request_payload_complex_json(){
        List<Integer> idArrayList=new ArrayList<Integer>();
        idArrayList.add(5);
        idArrayList.add(9);

        HashMap<String, Object> batterHashMap2=new HashMap<String,Object>();
        batterHashMap2.put("id",idArrayList);
        batterHashMap2.put("type","Chocolate");

        HashMap<String, Object> batterHashMap1=new HashMap<String,Object>();
        batterHashMap1.put("id","1001");
        batterHashMap1.put("type","Regular");

        List<HashMap<String,Object>> batterArrayList = new ArrayList<HashMap<String,Object>>();
        batterArrayList.add(batterHashMap1);
        batterArrayList.add(batterHashMap2);

        HashMap<String, List<HashMap<String,Object>>> battersHashMap=new HashMap<String,List<HashMap<String,Object>>>();
        battersHashMap.put("batter",batterArrayList);

        List<String> typeArrayList= new ArrayList<String>();
        typeArrayList.add("test1");
        typeArrayList.add("test2");

        HashMap<String,Object> toppingHashMap2 =new HashMap<String,Object>();
        toppingHashMap2.put("id","5002");
        toppingHashMap2.put("type", typeArrayList);

        HashMap<String, Object> toppingHashMap1= new HashMap<String,Object>();
        toppingHashMap1.put("id","5001");
        toppingHashMap1.put("type", "None");

        List<HashMap<String, Object>> toppingArrayList = new ArrayList<HashMap<String,Object>>();
        toppingArrayList.add(toppingHashMap1);
        toppingArrayList.add(toppingHashMap2);

        HashMap<String,Object> mainHashMap=new HashMap<String,Object>();
        mainHashMap.put("id","0001");
        mainHashMap.put("type","donut");
        mainHashMap.put("name","Cake");
        mainHashMap.put("ppu",0.55);
        mainHashMap.put("batters",battersHashMap);
        mainHashMap.put("topping", toppingArrayList);

        given().
                body(mainHashMap).
                when().
                post("/postComplexJSON").
                then().spec(customResponseSpecification).
                assertThat().
                body("msg",equalTo("Success"));


    }

    @Test
    public void validate_post_request_payload_complex_jsonAssignment(){
        List<Integer> idArrayListrgba1 =new ArrayList<Integer>();
        idArrayListrgba1.add(255);
        idArrayListrgba1.add(255);
        idArrayListrgba1.add(255);
        idArrayListrgba1.add(1);

        List<Integer> idArrayListrgba2 =new ArrayList<Integer>();
        idArrayListrgba2.add(0);
        idArrayListrgba2.add(0);
        idArrayListrgba2.add(0);
        idArrayListrgba2.add(1);

        HashMap<String,Object> codeOne=new HashMap<String,Object>();
        codeOne.put("rgba",idArrayListrgba1);
        codeOne.put("hex","#000");

        HashMap<String,Object> codeTwo =new HashMap<String,Object>();
        codeTwo.put("rgba",idArrayListrgba2);
        codeTwo.put("hex","#FFF");


        HashMap<String,Object> colorsOne=new HashMap<String,Object>();
        colorsOne.put("color","black");
        colorsOne.put("category","hue");
        colorsOne.put("type","primary");
        colorsOne.put("code",codeOne);

        HashMap<String,Object> colorsTwo=new HashMap<String,Object>();
        colorsTwo.put("color","white");
        colorsTwo.put("category","value");
        colorsTwo.put("code",codeTwo);


        List<HashMap<String,Object>> colorsArrayList = new ArrayList<HashMap<String,Object>>();
        colorsArrayList.add(0,colorsOne);
        colorsArrayList.add(1,colorsTwo);

        HashMap<String,Object> mainHashMap=new HashMap<String,Object>();
        mainHashMap.put("colors",colorsArrayList);


        given().
                body(mainHashMap).
                when().
                post("/postJsonExercise").
                then().spec(customResponseSpecification).
                assertThat().
                body("msg",equalTo("Success"));


    }



}
