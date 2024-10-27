package com.rest.fileUploadDownload;

import org.testng.annotations.Test;

import java.io.*;

import static io.restassured.RestAssured.given;

public class fileUploadDownload {
      /*
    * {
    "args": {},
    "data": {},
    "files": {
        "temp.txt": "data:application/octet-stream;base64,c29tZSBmaWxl"
    },
    "form": {
        "attributes": "{\"name\":\"temp.txt,\"parent\":{\"id\":\"123456\"}}"
    },*/

    @Test
    public void upload_file_multiple_form_data(){
        String attributes="{\"name\":\"temp.txt,\"parent\":{\"id\":\"123456\"}}";
        given()
                .baseUri("https://postman-echo.com")
                .multiPart("file", new File("src/main/resources/temp.txt"))
                .multiPart("attributes",attributes,"application/json")
                .log().all()
                .when()
                .post("/post")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }

    /*
    Headers:		Accept=*//* edna crta samo
    Content-Type=multipart/form-data
    Cookies:		<none>
    Multiparts:		------------
    Content-Disposition: form-data; name = file; filename = temp.txt
    Content-Type: application/octet-stream

    src\main\resources\temp.txt
				------------
    Content-Disposition: form-data; name = attributes; filename = file
    Content-Type: application/json

    {"name":"temp.txt,"parent":{"id":"123456"}}

            * */

    // first download the file and check in the network tab which is the request
    // https://github.com/appium/appium/blob/master/packages/appium/sample-code/apps/ApiDemos-debug.apk

    @Test
    public void download_file() throws IOException {
        // instead of bytes array we can extract the input stream
       // byte[] bytes
             InputStream is =
        given()
                .baseUri("https://raw.githubusercontent.com")
                .log().all()
                .when()
                .get("/appium/appium/master/packages/appium/sample-code/apps/ApiDemos-debug.apk")
                .then()
                .log().all()
                .extract()
                //.response().asByteArray();
                .response().asInputStream();

        // expecting the file to be downloaded in the root repository under that name below
        OutputStream os= new FileOutputStream(new File("ApiDemos-debug.apk"));
        byte [] bytes=new byte[is.available()];
        is.read(bytes);
        os.write(bytes);
        //os.write(bytes);
        os.close();
    }
}
