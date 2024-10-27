package com.rest.methods;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.*;

public class AutomatedGet {
     String access_token="secret";
    @Test
    public void validate_status_code(){
        // part of the given method sending the header, path parameteres, query parameteres, request body, authorization key
        // when represent the action/event to execute the request
        //then outcome, the result of the action we took, we can validate the outcome, response body, status code, response headers, cookies in the response

        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key",access_token).
        when().
                get("/workspaces").
        then().
                log().all(). // to have if we have some error for correct status 200
                assertThat().
                statusCode(200). //.
             //   log().all(); // to have after will not display the correct response body just the assertion error if we put status code 201
        // validate the get response body by name and type using groovy to fetch json object groovy playground
        body("workspaces.name",hasItems("My Workspace", "G1-2022/2023 - Automation", "Team Workspace", "Postman Homework - 1", "Postman Homework - 2", "Postman Homework - 3", "ExtraDomshnaTests","Postman Homework - 4", "Extra Postman 4 Homework", "Postman_Final_Extra", "SecureCash", "PostmanExamples", "G6", "HomeworkWorkSpace", "465", "G6_3", "G6_2", "G6_4", "PostmanClassExamples"),
                "workspaces.type",hasItems("personal", "team", "team", "team", "team", "team", "team", "personal", "team", "personal", "personal", "personal", "team", "team", "team", "team", "team", "personal"),
                "workspaces[0].name",equalTo("My Workspace"),
                "workspaces[0].name",is(equalTo("My Workspace")),
                "workspaces.size()",equalTo(19),
                "workspaces.name",hasItem("My Workspace"));
        // want to add more validations for my first workspaces -> workspaces[0].name is method is to make the assertion more readable
        //want to validate the size of the array
        // we can validate single item, one workspace instead all of them (hasItem)
        // if i change data it should fail
        //java.lang.AssertionError: 2 expectations failed.
        //JSON path workspaces[0].name doesn't match.
        //Expected: is "My Workspace1"
        //  Actual: My Workspace
        //
        //JSON path workspaces.size() doesn't match.
        //Expected: <18>
        //  Actual: <19>
    }
/* BITNO PODELBA NA SITE testovi posebno od prvoto
    @Test
    public void validate_status_code(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "secret").
                when().
                get("/workspaces").
                then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void validate_response_body(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "secret").
                when().
                get("/workspaces").
                then().
                log().all().
                assertThat().
                statusCode(200).
                body("workspaces.name", hasItems("Team Workspace", "My Workspace", "My Workspace2"),
                        "workspaces.type", hasItems("team", "personal", "personal"),
                        "workspaces[0].name", equalTo("Team Workspace"),
                        "workspaces[0].name", is(equalTo("Team Workspace")),
                        "workspaces.size()", equalTo(3),
                        "workspaces.name", hasItem("Team Workspace")
                );
    }

  */

    // Extract response
    // you can fetch a specific value from the response ant then you can use that value to make another api request
    // instead of hamcrest you want to use another assertion library need to extract the response, process it and use the assertion library
    //Response is an interface and create a variable of type Response and we want to print it we need to convert it as string
    // Even though the response has status code and header info, it will not be printed when executing the test,
    // we need to explicitly extract the status code and the headers and then print
    @Test
    public void extract_response(){
        Response res = given().
                given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", access_token).
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200).
                extract().
                response();
        System.out.println("response = " + res.asString());
    }

    // EXTRACT SINGLE VALUE 4 options from the response
    @Test
    public void extract_single_value_from_response(){
        String name = given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", access_token).
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200).
                extract().
                response().path("workspaces[0].name");
        System.out.println("workspace name = " + name);

        //   Response res =given(). JsonPath jsonPath = new JsonPath(res.asString());System.out.println("workspace name = " + JsonPath.from(res).getString("workspaces[0].name"));
        //    System.out.println("workspace name = " + jsonPath.getString("workspaces[0].name"));
        //  Response res =given().  System.out.println("workspace name = " + res.path("workspaces[0].name"));
    }

    @Test
    public void hamcrest_assert_on_extracted_response(){
        String name = given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", access_token).
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200).
                extract().
                response().path("workspaces[0].name");
        System.out.println("workspace name = " + name);

       // assertThat(name, equalTo("My Workspace"));
        // using testng instead of hamcrest
       Assert.assertEquals(name, "My Workspace");
        //   System.out.println("workspace name = " + JsonPath.from(res).getString("workspaces[0].name"));
        //    System.out.println("workspace name = " + jsonPath.getString("workspaces[0].name"));
        //    System.out.println("workspace name = " + res.path("workspaces[0].name"));
    }

    @Test
    public void validate_response_body_hamcrest_learnings(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", access_token).
                when().
                get("/workspaces").
                then().
                //               log().all().
                        assertThat().
                statusCode(200).
                // hasItems method even of you provide 2 elements it will pass, with contains we need to add all elements in strict order if not it will fail
                        // if we want to specifically check if array is not empty if we add only empty it will not be a problem if collection is array list etc
                body("workspaces.name", containsInAnyOrder("G1-2022/2023 - Automation","My Workspace", "Team Workspace", "Postman Homework - 1", "Postman Homework - 2", "Postman Homework - 3", "ExtraDomshnaTests","Postman Homework - 4", "Extra Postman 4 Homework", "Postman_Final_Extra", "SecureCash", "PostmanExamples", "G6", "HomeworkWorkSpace", "465", "G6_3", "G6_2", "G6_4", "PostmanClassExamples"),
                        "workspaces.name", is(not(emptyArray())),
                        "workspaces.name", hasSize(19),
                          //   "workspaces.name", everyItem(startsWith("Postman")) //it will fail cus not all elements start with postman
                                // applicable for maps
                        "workspaces[0]", hasKey("id"),
                        "workspaces[0]", hasValue("My Workspace"),
                        "workspaces[0]", hasEntry("id", "21b22ab6-7dfa-4df1-891c-189d5ae1d46f"),
                        "workspaces[0]", not(equalTo(Collections.EMPTY_MAP)),
                        "workspaces[0].name", allOf(startsWith("My"), containsString("Workspace"))
                );
        // we can go to hamcrest documentation for all kinds of methods
    }


}
