package com.rest.headers;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class HandlingHeaders {
     // there a request and response headers we can add custom headers added for authorization of a user
    // we need to set up testing env so we can send headers as part of the request/response we can use the postman mock server

    @Test
    public void multiple_headers(){
        Header header = new Header("header", "value1");
        Header matchHeader = new Header("x-mock-match-request-headers", "header");
        given().
                baseUri("https://0b883c06-00fa-45cd-b8e3-1f9d69a05d0d.mock.pstmn.io").
                header(header).
                header(matchHeader).
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200);
    }

    // using headers object, multiple headers in one
    @Test
    public void multiple_headers_using_Headers(){
        Header header = new Header("header", "value2");
        Header matchHeader = new Header("x-mock-match-request-headers", "header");

        Headers headers = new Headers(header, matchHeader);

        given().
                baseUri("https://0b883c06-00fa-45cd-b8e3-1f9d69a05d0d.mock.pstmn.io").
                headers(headers).
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200);
    }
    // using map object, multiple headers in one
// headers method is overloaded meaning it can accept argument of different type
    @Test
    public void multiple_headers_using_map(){
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header", "value2");
        headers.put("x-mock-match-request-headers", "header");

        given().
                baseUri("https://0b883c06-00fa-45cd-b8e3-1f9d69a05d0d.mock.pstmn.io").
                headers(headers).
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200);
    }

    // header can have multiple values, map cannot have duplicate keys cannot use hash map

    @Test
    public void multi_value_header_in_the_request(){
        Header header1 = new Header("multiValueHeader", "value1");
        Header header2 = new Header("multiValueHeader", "value2");

        Headers headers = new Headers(header1, header2);

        given().
                baseUri("https://0b883c06-00fa-45cd-b8e3-1f9d69a05d0d.mock.pstmn.io").
                //           headers(headers).
                        headers(headers).
                log().headers().
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200);
    }

    // Assert response headers
    @Test
    public void assert_response_headers(){
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header", "value1");
        headers.put("x-mock-match-request-headers", "header");

        given().
                baseUri("https://0b883c06-00fa-45cd-b8e3-1f9d69a05d0d.mock.pstmn.io").
                headers(headers).
                when().
                get("/get").
                then().
                assertThat().
                statusCode(200).
                headers("responseHeader", "resValue1",
                        "X-RateLimit-Limit", "120"); // we can pass actual expected value, the x one is an error that can happen to check
    }

    // Extract response headers

    @Test
    public void extract_response_headers(){
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header", "value1");
        headers.put("x-mock-match-request-headers", "header");

        Headers extractedHeaders = given().
                baseUri("https://0b883c06-00fa-45cd-b8e3-1f9d69a05d0d.mock.pstmn.io").
                headers(headers).
                when().
                get("/get").
                then().
                assertThat().
                statusCode(200).
                extract().
                headers();

        for(Header header: extractedHeaders){
            System.out.print("header name = " + header.getName() + ", ");
            System.out.println("header value = " + header.getValue());
        }

/*
        extract response headers
 System.out.println("header name = " + extractedHeaders.get("responseHeader").getName());
        System.out.println("header value = " + extractedHeaders.get("responseHeader").getValue());
        System.out.println("header value = " + extractedHeaders.getValue("responseHeader"));*/
    }
    @Test
    public void extract_multivalue_response_header(){
        // header that has multiple values
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header", "value1");
        headers.put("x-mock-match-request-headers", "header");

        Headers extractedHeaders = given().
                baseUri("https://0b883c06-00fa-45cd-b8e3-1f9d69a05d0d.mock.pstmn.io").
                headers(headers).
                when().
                get("/get").
                then().
                assertThat().
                statusCode(200).
                extract().
                headers();

        List<String> values = extractedHeaders.getValues("multiValueHeader"); //will return multiple values in form of list and look through the list to fetch individual value
        for(String value:values){
            System.out.println(value);
        }
    }

}
