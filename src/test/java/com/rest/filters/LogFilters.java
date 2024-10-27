package com.rest.filters;

import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static io.restassured.RestAssured.given;

public class LogFilters {
    // https://github.com/rest-assured/rest-assured/wiki/Usage#filters
    // print response and request log
    @Test
    public void loggingFilter(){
        given()
                .baseUri("https://postman-echo.com")
                .filter(new RequestLoggingFilter(LogDetail.BODY))
                .filter(new ResponseLoggingFilter(LogDetail.STATUS))
          //      .log().all()
                .when()
                .get("/get")
                .then()
          //      .log().all()
                .assertThat()
                .statusCode(200);
    }

    // print to log file for debugging purposes
    // https://www.javadoc.io/doc/io.rest-assured/rest-assured/latest/io/restassured/filter/Filter.html
    @Test
    public void loggingFilterFile() throws FileNotFoundException {
        PrintStream FileOutputStream = new PrintStream(new File("restAssured.log"));
        given()
                .baseUri("https://postman-echo.com")
                .filter(new RequestLoggingFilter(LogDetail.BODY,FileOutputStream))
                .filter(new ResponseLoggingFilter(LogDetail.STATUS,FileOutputStream))
                //      .log().all()
                .when()
                .get("/get")
                .then()
                //      .log().all()
                .assertThat()
                .statusCode(200);
    }
}
