package com.rest.logging;

import io.restassured.config.LogConfig;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class Logging {
        // this is used for debugging purposes
    @Test
    public void request_response_logging(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "secret").
                log().all(). //parameters(). //body(). //cookies(). //headers(). // to log the entire request // we don't want to print the entire request or response only headers
                when().
                get("/workspaces").
                then().
                log().all(). //cookies(). //headers(). //body(). // to log response // we don't want to print the entire request or response only body
                assertThat().
                statusCode(200);
    }

    @Test
    public void log_only_if_error(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "secret").
                log().all().
                when().
                get("/workspaces").
                then().
                log().ifError(). //will print the logs only if there is an error in the response
                assertThat().
                statusCode(200);
    }

    @Test
    public void log_only_if_validation_fails(){
        // request is successful but there is fail in validation, in that case I want to log response/request info
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "secret").
                config(config.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails())).
                //            log().ifValidationFails().
                        when().
                get("/workspaces").
                then().
                //            log().ifValidationFails().
                        assertThat().
                statusCode(201);
    }

    @Test
    public void logs_blacklist_header(){
        Set<String> headers = new HashSet<String>();
        // blacklist this headers
        headers.add("X-Api-Key");
        headers.add("Accept");
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "secret").
                config(config().logConfig(LogConfig.logConfig().blacklistHeaders(headers))). // blacklist multiple headers
                log().all(). // !!!!! want to log the request info, but we don't want to print header info 'cus it is sensitive info if user token we get from header we can blacklist it
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200);
    }
}
