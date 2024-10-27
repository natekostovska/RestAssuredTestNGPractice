package com.rest.JSONSchemaValidation;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class JSONSchemaValidation {
    // vocabulary that allows you to annotate and validate JSON documents, describes data formats
    // confirms that data type is correctly provided boolean true/false, what value a field can have
    // https://www.jsonschema.net/app/schemas/0
    // https://github.com/rest-assured/rest-assured/wiki/Usage#json-schema-validation
    // we are doing it in the response body get("/products").then().assertThat().body(matchesJsonSchemaInClasspath("products-schema.json"));
// https://mvnrepository.com/artifact/io.rest-assured/json-schema-validator/5.5.0
    // can get it from our dev team which fields are mandatory or default
    // error: instance type (string) does not match any allowed primitive type (allowed: ["object"])

    @Test
    public void validateJsonSchema(){

        given()
                .baseUri("https://postman-echo.com")
                .log().all()
                .when()
                .get("/get")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("EchoGet.json"));
    }

}
