package com.rest.handlingRequestParameters;

import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class RequestParameters {
     /*Postman Echo is a service you can use to test your REST clients and make sample API calls.
    It provides endpoints for GET, POST, PUT, various auth mechanisms and other utility endpoints.
    The documentation for the endpoints as well as example responses can be found at https://postman-echo.com*/

    // get echo https://postman-echo.com/get we do not send any arguments

 /*   {
        "args": {},
        "headers": {
        "host": "postman-echo.com",
                "x-forwarded-proto": "http",
                "x-request-start": "t=1723492001.316",
                "connection": "close",
                "x-forwarded-port": "443",
                "x-amzn-trace-id": "Root=1-66ba66a1-6348fb8616a4611f0f107760",
                "user-agent": "PostmanRuntime/7.40.0",
                "accept": "*//*", // samo edna crta stavi !!!
                "cache-control": "no-cache",
                "postman-token": "06b8793e-617a-428b-b088-2df8a373511f",
                "accept-encoding": "gzip, deflate, br"
    },
        "url": "http://postman-echo.com/get"
    } */

    /*
    What are query parameters?
     Key value pairs that we are going to send along with the url. There is a specific format needs to be followed
     Anything we see after the ? that is a query parameter ?foo1=bar1&foo2=bar2
        "args": {
        "foo1": "bar1"
    },
normally it wont send the query parameter in the response we can see it in request params/ query params and request uri
     */

    @Test
    public void single_query_parameter() {
        given().baseUri("https://postman-echo.com")
                //    .param("foo1","bar1") // generic, both methods will work
                .queryParam("foo2", "bar2") // specific
                .log().all()
                .when().get("/get")
                .then().log().all().assertThat().statusCode(200);
    }

    @Test
    public void multiple_query_parameters() {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("foo1", "bar1");
        queryParams.put("foo2", "bar2");
        given().baseUri("https://postman-echo.com")
                //    .param("foo1","bar1") // generic, both methods will work
                //   .queryParam("foo1","bar1") // specific
                //   .queryParam("foo2","bar2")
                // we can use hash map to send multiple query parameters
                .queryParams(queryParams)
                .log().all()
                .when().get("/get")
                .then().log().all().assertThat().statusCode(200);
    }

// query parameter that has multiple values

    @Test
    public void multi_value_query_parameter() {
        given().baseUri("https://postman-echo.com")
                //    .param("foo1","bar1") // generic, both methods will work
                .queryParam("foo1", "bar1;bar2;bar3") // specific, instead of comma we can use ; semi column for separating values
                .log().all()
                .when().get("/get")
                .then().log().all().assertThat().statusCode(200);

        // https://postman-echo.com/get?foo1=bar1%2Cbar2%2Cbar3 url encoding with ,
        // https://postman-echo.com/get?foo1=bar1%3Bbar2%3Bbar3 with ;
    }

    // path parameter want to extract info for user with id 1

    @Test
    public void path_parameter() {
        given()
                .baseUri("https://regres.in")
                // key can be any value that we send in the url to get specific info
                .pathParam("userId", "2")
         /*       .pathParam("bookId", "1")
                //if multiple parameters we can use hash map
                .pathParams()*/
                .log().all()
              //  .when().get("/api/users/{userId}/{bookId}")
                .when()
                .get("/api/users/{userId}")
                .then().log().all().assertThat().statusCode(200);

    }
}
