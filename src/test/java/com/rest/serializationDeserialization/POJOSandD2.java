package com.rest.serializationDeserialization;

import com.rest.pojo.workspace.Workspace;
import com.rest.pojo.workspace.WorkspaceRoot;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class POJOSandD2 {
    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.postman.com").
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

    // Serialize Jackson pojo
    @Test
    public void serialize_json_using_jackson() {
        Workspace workspace=new Workspace("myWorkspace4","personal","description");
        HashMap<String,String> myHashMap=new HashMap<String,String>();
        workspace.setMyHashMap(myHashMap); // empty value
        WorkspaceRoot workspaceRoot=new WorkspaceRoot(workspace);


        WorkspaceRoot deserializedWorkspaceRoot= given().
                body(workspaceRoot).
                when().
                post("/workspaces").
                then().spec(responseSpecification)
                .extract().
                response().
                as(WorkspaceRoot.class);
        Assert.assertEquals(deserializedWorkspaceRoot.getWorkspace().getName(),workspaceRoot.getWorkspace().getName());
        assertThat(deserializedWorkspaceRoot.getWorkspace().getId(),matchesPattern("^[a-z0-9-]{36}$"));
    }

    // data provider
    @Test(dataProvider = "workspace")
    public void workspace_serialize_deserialize(String name, String type, String description) {
        Workspace workspace=new Workspace(name,type,description);
        WorkspaceRoot workspaceRoot=new WorkspaceRoot(workspace);


        WorkspaceRoot deserializedWorkspaceRoot= given().
                body(workspaceRoot).
                when().
                post("/workspaces").
                then().spec(responseSpecification)
                .extract().
                response().
                as(WorkspaceRoot.class);
        Assert.assertEquals(deserializedWorkspaceRoot.getWorkspace().getName(),workspaceRoot.getWorkspace().getName());
        assertThat(deserializedWorkspaceRoot.getWorkspace().getId(),matchesPattern("^[a-z0-9-]{36}$"));
    }

    @DataProvider(name="workspace")
    public Object[][] getWorkspace(){
        return new Object[][]{
                {"myWorkspace6","personal","description"},
                {"myWorkspace7","team","description"}
        };
    }
}
