package com.spotify.oauth2.api.applicationApi;

import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oauth2.api.Route.API;
import static com.spotify.oauth2.api.Route.TOKEN;
import static com.spotify.oauth2.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestResource {
    public static Response post(String path, String token, Object requestPlaylist) {
        return given(getRequestSpec()).
                body(requestPlaylist).
                auth().oauth2(token).
                //instead of the header method using authorization and bearer text we can use the auth one
               // header("Authorization", "Bearer " + token).
                when().
                post(path)
                .then().spec(getResponseSpec())
                .extract()
                .response();
    }

    // this is for the token
    public static Response postAccount(HashMap<String,String> formParams){
        return given(getAccountRequestSpec())
                .formParams(formParams)
                .when().post(API+ TOKEN)
                .then().spec(getResponseSpec())
                .extract()
                .response();
    }

    public static Response get(String path, String token) {
        return given(getRequestSpec()).
                auth().oauth2(token).
                when().
                get(path)
                .then().spec(getResponseSpec())
                .extract()
                .response();
    }

    public static Response update(String path, String token, Object requestPlaylist){
       return given(getRequestSpec())
                .body(requestPlaylist).
               auth().oauth2(token).
                when().
                put(path)
                .then().spec(getResponseSpec())
               .extract()
               .response();
    }

}
