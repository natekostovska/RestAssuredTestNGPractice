package com.rest.authenticationAuthorization;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Base64;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class GoogleOAuth2_0Automate {
    // take refresh token then execute renew token and take token from there
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String access_token = "secret";

    @BeforeClass
    public void beforeClass() {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://gmail.googleapis.com").
                addHeader("Authorization", "Bearer " + access_token).
                setContentType(ContentType.JSON).
                log(LogDetail.ALL);

        requestSpecification=requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();

    }

    // base path from get user profile url
    @Test
    public void getUserProfile(){
        given(requestSpecification).
                basePath("/gmail/v1/").pathParam("userid","qatestnatasha@gmail.com").
                when().
                get("/users/{userid}/profile").
                then().spec(responseSpecification);
    }

    // The entire email message is allowed to be sent in an RFC 2822 format and the string should be base64url encoded.

    @Test
    public void sendMessage(){

        String msg="From: qatestnatasha@gmail.com\n" +
                "To: qatestnatasha@gmail.com\n" +
                "Subject: Rest Assured Test Email\n" +
                "\n" +
                "Sending from Rest Assured";

        String base64URLEncodeMsg= Base64.getUrlEncoder().encodeToString(msg.getBytes());

        HashMap<String,String> payload= new HashMap<>();
        payload.put("raw", base64URLEncodeMsg);

        given(requestSpecification).
                basePath("/gmail/v1/").
                pathParam("userid","qatestnatasha@gmail.com").
                body(payload).
                when().
                post("/users/{userid}/messages/send").
                then().spec(responseSpecification);
    }

    @Test
    public void listTotalNumberOfMessages(){

        given(requestSpecification).
                basePath("/gmail/v1/").pathParam("userid","qatestnatasha@gmail.com").
                when().
                get("/users/{userid}/messages").
                then().spec(responseSpecification);
    }

    @Test
    public void modifyMessage(){

        String msg="INBOX";

        String base64URLEncodeMsg= Base64.getUrlEncoder().encodeToString(msg.getBytes());

        HashMap<String,String> payload= new HashMap<>();
        payload.put("removeLabelIds", base64URLEncodeMsg);

        given(requestSpecification).
                basePath("/gmail/v1/").
                pathParam("userId","qatestnatasha@gmail.com").
                pathParam("id","190c59c9a3e85726").
                body(payload).
                when().
                post("/users/{userId}/messages/{id}/modify");
    }

    @Test
    public void deleteMessages(){

        given(requestSpecification).
                basePath("/gmail/v1/").
                pathParam("userId","qatestnatasha@gmail.com").
                pathParam("id","190c5a3e1a8bf146").
                when().
                delete("/users/{userId}/messages/{id}");
    }

    @Test
    public void getMessages(){

        given(requestSpecification).
                basePath("/gmail/v1/").
                pathParam("userid","qatestnatasha@gmail.com").
                pathParam("id","190c59c9a3e85726").
                when().
                get("/users/{userid}/messages/{id}").
                then().spec(responseSpecification);
    }

}
