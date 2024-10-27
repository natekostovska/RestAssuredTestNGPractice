package com.rest.authenticationAuthorization;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class FormBasedAuthentication {
    /*
     * Session based authentication -> the server maintenance the session
     *
     *  Post/sign in {username, password}
     * Server stores the session -> Cookie: Session ID
     * Get resource Cookie: Session ID
     * Use cookie to get user info
     * Resource
     *
     * Include CSRF token submitting a web request they did not intend
     * https://github.com/rest-assured/rest-assured/wiki/Usage#csrf
     *
     *
     * Form Based Authentication
     * https://github.com/rest-assured/rest-assured/wiki/Usage#form-authentication
     * https://docs.oracle.com/cd/E15217_01/doc.1014/e12488/v2form.htm web session based auth method and form name
     * need additional request made to the server in order to retrieve the webpage with the login details try two parse the page
     * new FormAuthConfig
     * first input field, second input field
     *
     *https://localhost:7777/
     * den den123
     *83EFE9ED7C11034BE90C2FAF4252FC44
     * */

    @BeforeClass
    public void beforeClass() {
        RestAssured.requestSpecification = new RequestSpecBuilder().
                setRelaxedHTTPSValidation().
                setBaseUri("https://localhost:7777").
                build();
    }

    @Test
    public void form_authentication_using_csrf_token() {
        SessionFilter filter = new SessionFilter();
        given().
                csrf("/login"). //fetches csrf token from /login endpoint
                auth().form("dan", "dan123", new FormAuthConfig("/signin", "txtUsername", "txtPassword")). // Those are found in response html for log in action and input id, name
                filter(filter). // will store it (session id) in the session object if the name is different it wont work JSESSIONID, then we will need to paste the name explicitly
                log().all().
                when().
                get("/login").
                then().
                log().all().
                assertThat().
                statusCode(200);

        System.out.println("Session id = " + filter.getSessionId());


        given().
                sessionId(filter.getSessionId()).
                log().all().
                when().
                get("/profile/index").
                then().
                log().all().
                assertThat().
                statusCode(200).
                body("html.body.div.p",equalTo("This is User Profile\\Index. Only authenticated people can see this"));
    }

    @Test
    public void form_authentication_using_csrf_token_cookie_example() {
        SessionFilter filter = new SessionFilter();
        given().
                csrf("/login"). //fetches csrf token from /login endpoint
                auth().form("dan", "dan123", new FormAuthConfig("/signin", "txtUsername", "txtPassword")). // Those are found in response html for log in action and input id, name
                filter(filter). // will store it (session id) in the session object if the name is different it wont work JSESSIONID, then we will need to paste the name explicitly
                log().all().
                when().
                get("/login").
                then().
                log().all().
                assertThat().
                statusCode(200);

        System.out.println("Session id = " + filter.getSessionId());

        Cookie cookie= new Cookie.Builder("JSESSIONID",filter.getSessionId()).setSecured(true).setHttpOnly(true).setComment("my cookie").build();
        Cookie cookie1=new Cookie.Builder("dummy","dummyValue").build();

        Cookies cookies=new Cookies(cookie,cookie1);

        given().
                //cookie("JSESSIONID",filter.getSessionId()). // we can send entire cookie
              //  cookie(cookie). // to add additional attributes with cookie builder
                cookies(cookies).      // sending multiple cookies in the header
                log().all().
                when().
                get("/profile/index").
                then().
                log().all().
                assertThat().
                statusCode(200).
                body("html.body.div.p",equalTo("This is User Profile\\Index. Only authenticated people can see this"));
    }

    @Test
    public void fetch_single_cookie(){
       Response response= given().
                log().all().
        when().
                get("/profile/index").
        then().
                log().all().
                assertThat().
                statusCode(200).
                extract().
                response();

        System.out.println(response.getCookie("JSESSIONID"));
        System.out.println(response.getDetailedCookie("JSESSIONID"));
    }

    @Test
    public void fetch_multiple_cookies(){
        Response response= given().
                log().all().
                when().
                get("/profile/index").
                then().
                log().all().
                assertThat().
                statusCode(200).
                extract().
                response();
        Map<String,String> cookies=response.getCookies(); // collected cookies as form of hashmap one or multiple cookies, here returns only cookie value and name
        for (Map.Entry<String,String>entry:cookies.entrySet()){
            System.out.println("cookie name: "+entry.getKey());
            System.out.println("cookie value: "+entry.getValue());
        }
        //to get detailed information about cookie including attributes
       Cookies cookies1= response.getDetailedCookies();
        List<Cookie> cookieList=cookies1.asList();
        for (Cookie cookie:cookieList){
            System.out.println("cookie = "+ cookie.toString());
        }

      /*  System.out.println(response.getCookie("JSESSIONID"));
        System.out.println(response.getDetailedCookie("JSESSIONID"));*/
    }

}
