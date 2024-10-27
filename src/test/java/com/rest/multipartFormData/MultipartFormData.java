package com.rest.multipartFormData;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class MultipartFormData {
    // for post and put request the request body can be sent as a form data as key value pairs
    // use to send file, the file is not sent in one go to the server https://github.com/rest-assured/rest-assured/blob/master/examples/rest-assured-itest-java/src/test/java/io/restassured/itest/java/MultiPartUploadITest.java
/*Multiparts:		------------
				Content-Disposition: form-data; name = foo1; filename = file
				Content-Type: text/plain

				bar1*/
    @Test
    public void multipart_form_data(){
        given()
                .baseUri("https://postman-echo.com")
                .multiPart("foo1","bar1")
                .multiPart("foo2","bar2")
                .log().all()
                .when()
                .post("/post")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }

    /*Multiparts:		------------
				Content-Disposition: form-data; name = foo1; filename = file
				Content-Type: text/plain

				bar1
				------------
				Content-Disposition: form-data; name = foo2; filename = file
				Content-Type: text/plain

				bar2*/
}
