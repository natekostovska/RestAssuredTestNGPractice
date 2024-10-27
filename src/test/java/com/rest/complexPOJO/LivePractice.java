package com.rest.complexPOJO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.pojo.collection.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.ValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

public class LivePractice {
    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.getpostman.com").
                addHeader("X-Api-Key", "secret").
                setContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    // build payload and serialize, collection package in rest, the collection will be created in postman
    // @JsonIgnoreProperties(ignoreUnknown = true) //will ignore any new property in the response that is not available in the pojo classes to be added on class level

    @Test
    public void complex_pojo_create_collection() throws JsonProcessingException, JSONException {
        Header header = new Header("Content-Type", "application/json");
        List<Header> headerList = new ArrayList<Header>();
        headerList.add(header);

        Body body = new Body("raw", "{\"data\": \"123\"}");

        /*RequestBase request=new RequestBase("https://postman-echo.com/post","POST",headerList,body,
                "This is a sample POST Request");*/

        RequestRequest request = new RequestRequest("https://postman-echo.com/post", "POST", headerList, body,
                "This is a sample POST Request");

        /*RequestRootBase requestRoot=new RequestRootBase("Sample POST Request",request);
        List<RequestRootBase> requestRootList=new ArrayList<RequestRootBase>();
        requestRootList.add(requestRoot);*/

        RequestRootRequest requestRoot = new RequestRootRequest("Sample POST Request", request);
        List<RequestRootRequest> requestRootList = new ArrayList<RequestRootRequest>();
        requestRootList.add(requestRoot);

        /*FolderBase folder=new FolderBase("This is a folder",requestRootList);
        List<FolderBase> folderList=new ArrayList<FolderBase>();
        folderList.add(folder);*/

        FolderRequest folder = new FolderRequest("This is a folder", requestRootList);
        List<FolderRequest> folderList = new ArrayList<FolderRequest>();
        folderList.add(folder);

        Info info = new Info("Sample Collection1", "This is just a sample collection.",
                "https://schema.getpostman.com/json/collection/v2.1.0/collection.json");

       /* CollectionBase collection=new CollectionBase(info,folderList);

        CollectionRootBase collectionRoot=new CollectionRootBase(collection);*/

        CollectionRequest collection = new CollectionRequest(info, folderList);

        CollectionRootRequest collectionRoot = new CollectionRootRequest(collection);

// we need to extract uid from the collection to perform the get request deserialization and assert full body
        String collectionUid = given().
                body(collectionRoot).
                when().
                post("/collections").
                then().spec(responseSpecification).
                extract().
                response().path("collection.uid");

     /*  CollectionRootBase deserializationCollectionRoot= given().
                pathParam("collectionUid",collectionUid).
        when().
                get("/collections/{collectionUid}").
        then().spec(responseSpecification).
                extract().
                response().
                as(CollectionRootBase.class);*/

        CollectionRootResponse deserializationCollectionRoot = given().
                pathParam("collectionUid", collectionUid).
                when().
                get("/collections/{collectionUid}").
                then().spec(responseSpecification).
                extract().
                response().
                as(CollectionRootResponse.class);

        // JsonAssert library need Json strings as inputs, can do that with object mapper
        ObjectMapper objectMapper = new ObjectMapper();
        String collectionRootStr = objectMapper.writeValueAsString(collectionRoot);
        String deserializedCollectionRootStr = objectMapper.writeValueAsString(deserializationCollectionRoot);

        // We need to exclude URL as it is an object in the response, the data type is different from the request.
        // The JSON assert library will be useful as it gives option to exclude certain fields from full json body matching
        // https://mvnrepository.com/artifact/org.skyscreamer/jsonassert/1.5.1

        JSONAssert.assertEquals(collectionRootStr, deserializedCollectionRootStr,
                new CustomComparator(JSONCompareMode.STRICT_ORDER,
                        new Customization("collection.item[*].item[*].request.url", new ValueMatcher<Object>() {
                            public boolean equal(Object o1, Object o2) {
                                return true;
                            }
                        })));

        // extract the field that we are asserting with different data types url, with for we have all urls from the request payload
        List<String> UrlRequestList = new ArrayList<String>();
        List<String> UrlResponseList = new ArrayList<String>();
        for (RequestRootRequest requestRootRequest : requestRootList) {
            System.out.println("url from request payload: " + requestRootRequest.getRequest().getUrl());
            UrlRequestList.add(requestRootRequest.getRequest().getUrl());
            //    UrlRequestList.add("http://dummy.com"); java.lang.AssertionError:
            //Expected: iterable with items ["http://dummy.com"] in any order
            //     but: not matched: "https://postman-echo.com/post"

        }
        List<FolderResponse> folderResponseList = deserializationCollectionRoot.getCollection().getItem();
        for (FolderResponse folderResponse : folderResponseList) {
            List<RequestRootResponse> requestRootResponseList = folderResponse.getItem();
            for (RequestRootResponse requestRootResponse : requestRootResponseList) {
                URL url = requestRootResponse.getRequest().getUrl();
                System.out.println("url from response: " + url.getRaw());
                UrlResponseList.add(url.getRaw());
            }
        }

        assertThat(UrlResponseList, containsInAnyOrder(UrlRequestList.toArray()));
        Assert.assertEquals(UrlResponseList, UrlRequestList);
    }

    // to assert it we will call get request and make comparison. Deserialize and assert full body
    /* If i execute get collections request with url {{baseUrl}}/collections and find the Sample Collection1
    * "id": "6f6f097f-25f5-4860-a589-e03ae42b7f65",
            "name": "Sample Collection1",
            "owner": "25261691",
            "createdAt": "2024-08-15T15:33:40.000Z",
            "updatedAt": "2024-08-15T15:33:40.000Z",
            "uid": "25261691-6f6f097f-25f5-4860-a589-e03ae42b7f65",
            "isPublic": false
            *
            *
            * IF added uid to the url {{baseUrl}}/collections/25261691-6f6f097f-25f5-4860-a589-e03ae42b7f65 and execute will retrieve
            * the response body i added above (similar)
            *
            * {
    "collection": {
        "info": {
            "_postman_id": "6f6f097f-25f5-4860-a589-e03ae42b7f65",
            "name": "Sample Collection1",
            "description": "This is just a sample collection.",
            "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json",
            "updatedAt": "2024-08-15T15:33:40.000Z",
            "createdAt": "2024-08-15T15:33:40.000Z",
            "lastUpdatedBy": "25261691",
            "uid": "25261691-6f6f097f-25f5-4860-a589-e03ae42b7f65"
        },
        "item": [
            {
                "name": "This is a folder",
                "item": [
                    {
                        "name": "Sample POST Request",
                        "id": "809d12d2-7377-41c8-91d2-4e71a8de4bc9",
                        "request": {
                            "method": "POST",
                            "header": [
                                {
                                    "key": "Content-Type",
                                    "value": "application/json"
                                }
                            ],
                            "body": {
                                "mode": "raw",
                                "raw": "{\"data\": \"123\"}"
                            },
                            "url": {
                                "raw": "https://postman-echo.com/post",
                                "protocol": "https",
                                "host": [
                                    "postman-echo",
                                    "com"
                                ],
                                "path": [
                                    "post"
                                ]
                            },
                            "description": "This is a sample POST Request"
                        },
                        "response": [],
                        "uid": "25261691-809d12d2-7377-41c8-91d2-4e71a8de4bc9"
                    }
                ],
                "id": "1f09b307-6b60-46b4-b33b-8fadb7f67eba",
                "uid": "25261691-1f09b307-6b60-46b4-b33b-8fadb7f67eba"
            }
        ]
    }
}
    * */

    /*
     * Challenges
     * Different data type for "url" field in payload and response. Not asserting the url field
     * It is string in the request payload and object in response payload
     * */

    // Automate one more test case with empty collection
    @Test
    public void simple_pojo_create_collection() throws JsonProcessingException, JSONException {
        List<FolderRequest> folderList = new ArrayList<FolderRequest>();

        Info info = new Info("Sample Collection2", "This is just a sample collection.",
                "https://schema.getpostman.com/json/collection/v2.1.0/collection.json");

        CollectionRequest collection = new CollectionRequest(info, folderList);
        CollectionRootRequest collectionRoot = new CollectionRootRequest(collection);

// we need to extract uid from the collection to perform the get request deserialization and assert full body
        String collectionUid = given().
                body(collectionRoot).
                when().
                post("/collections").
                then().spec(responseSpecification).
                extract().
                response().path("collection.uid");

        CollectionRootResponse deserializationCollectionRoot = given().
                pathParam("collectionUid", collectionUid).
                when().
                get("/collections/{collectionUid}").
                then().spec(responseSpecification).
                extract().
                response().
                as(CollectionRootResponse.class);

        // JsonAssert library need Json strings as inputs, can do that with object mapper
        ObjectMapper objectMapper = new ObjectMapper();
        String collectionRootStr = objectMapper.writeValueAsString(collectionRoot);
        String deserializedCollectionRootStr = objectMapper.writeValueAsString(deserializationCollectionRoot);

        assertThat(objectMapper.readTree(collectionRootStr),equalTo(objectMapper.readTree(deserializedCollectionRootStr)));

        // We need to exclude URL as it is an object in the response, the data type is different from the request.
        // The JSON assert library will be useful as it gives option to exclude certain fields from full json body matching
        // https://mvnrepository.com/artifact/org.skyscreamer/jsonassert/1.5.1

      /*  JSONAssert.assertEquals(collectionRootStr, deserializedCollectionRootStr,
                new CustomComparator(JSONCompareMode.STRICT_ORDER,
                        new Customization("collection.item[*].item[*].request.url", new ValueMatcher<Object>() {
                            public boolean equal(Object o1, Object o2) {
                                return true;
                            }
                        })));*/

        // The idea is to create multiple test cases using the pojo classes
        /*
         * Collection With Multiple Folders
         * Collection With One Folder And Multiple Requests inside the folder
         * Collection with only one request
         * Collection with a request with multiple headers
         *
         *
         * */

    }
}
