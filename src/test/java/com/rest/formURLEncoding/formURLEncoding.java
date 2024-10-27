package com.rest.formURLEncoding;

import io.restassured.config.EncoderConfig;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class formURLEncoding {
    // to send body as this form & umpersent encoding on space=%20

    @Test
    public void form_urlencoded(){
        given()
                .baseUri("https://postman-echo.com")
                // we are telling rest assured not to send default content charset -> charset=ISO-8859-1
                .config(config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .formParam("key1","value1")
                .formParam("key 2","value 2")
                .log().all()
                .when()
                .post("/post")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }

}
