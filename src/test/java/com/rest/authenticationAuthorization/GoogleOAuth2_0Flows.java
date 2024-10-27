package com.rest.authenticationAuthorization;

public class GoogleOAuth2_0Flows {
    // FROM POSTMAN -> My workspace

    // https://oauth.net/code/java/
    // https://developers.google.com/identity/protocols/oauth2
    // Click on create project to create an application 12 projects in total
    // https://console.cloud.google.com/apis/dashboard?pli=1&project=myrestassuredtest-429513&supportedpurview=project
    // Enable gmail api -> Navigate to the library and search gmail api -> enable https://developers.google.com/gmail/api/reference/rest?apix=true
    // From dashboard to create credentials https://console.cloud.google.com/home/dashboard?project=myrestassuredtest-429513&supportedpurview=project

    // Creating consent screen https://console.cloud.google.com/apis/credentials/consent?project=myrestassuredtest-429513&supportedpurview=project
    // External and create it
    // Add scope gmail api mail.google.com and the first two options from the first page -> update
    // Add test users available that can have access to the app/ add users
    // https://console.cloud.google.com/apis/credentials/consent?project=myrestassuredtest-429513&supportedpurview=project

    // Creating client credentials https://console.cloud.google.com/apis/credentials?project=myrestassuredtest-429513&supportedpurview=project
    // Create OAuth client ID
    // Add a name Rest Assured Client and provide a dummy redirect URL https://localhost:8080
    // Create -> Client id 374322425555-rd35kb47ftv1094pt0onluea9ltl0g5h.apps.googleusercontent.com
    // client secret -> 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
    // https://www.udemy.com/course/rest-assured-api-automation/learn/lecture/25233532#overview

    // Authorize app
    // https://developers.google.com/identity/protocols/oauth2/web-server
    // Select https/rest
/*
    https://accounts.google.com/o/oauth2/v2/auth?
    scope=https%3A//www.googleapis.com/auth/drive.metadata.readonly&
            access_type=offline&
            include_granted_scopes=true&
            response_type=code&
            state=state_parameter_passthrough_value&
            redirect_uri=https%3A//oauth2.example.com/code&
            client_id=client_id*/

}

// In postman change parameters for redirect url and client id; paste the url in browser and then click in the account ->
//Google hasn’t verified this app click continue, Rest Assured App wants access to your Google Account will redirect to localhost 8080 no page
// access token fetch when pasting the url on browser from postman
// auth code 4/0Ac short lived
//https://mail.google.com/
// Fetch Access Token and Refresh Token
// put request url: https://oauth2.googleapis.com/token
// body

/*    "access_token": "yaARMSFQHGX2MisG3zV-u-ePUK7fw399a5TQ0171",
            "expires_in": 3599,
            "refresh_token": "1//09oj_A7kA2aNmxHoOO6JX0",
            "scope": "https://mail.google.com/",
            "token_type": "Bearer"*/

// if the access token expires we can use the refresh token we don't need to open the browser and go through the login flow again
// if the user is loged in, in the browser we can use the refresh token to generate new tokens as many times we want


/*
*  Renew Access Token
*
*  https://oauth2.googleapis.com/token
*  create post request with body info execute
*    "access_token": "ya29.agYKAWMSARASFQHGX2MieW3r1n_xoC7EJaG8IURVWw0171",
    "expires_in": 3599,
    "scope": "https://mail.google.com/",
    "token_type": "Bearer"
*
*
* */


/*
*  Now that we have the access token we can use the gmail api to fetch the user profile
*  Can send the access token using the authorization header
* https://developers.google.com/gmail/api/reference/rest
* https://developers.google.com/gmail/api/reference/rest/v1/users/getProfile
* GET https://gmail.googleapis.com/gmail/v1/users/{userId}/profile
*
* https://gmail.googleapis.com/gmail/v1/users/qatestnatasha@gmail.com/profile
* In the headers add parameter Authorization Bearer the accesstokenfromabove
* Execute the request
*
*  "emailAddress": "qatestnatasha@gmail.com",
    "messagesTotal": 3,
    "threadsTotal": 3,
    "historyId": "1692"
* */

/*
*   Execute send email post request
*
* https://gmail.googleapis.com/gmail/v1/users/qatestnatasha@gmail.com/messages/send
* In the headers add parameter Authorization Bearer the accesstokenfromabove
* https://developers.google.com/gmail/api/reference/rest/v1/users.messages#Message
* https://ostermiller.org/calc/encode.html -> helps to encode string to base 64 format
*
*From: qatestnatasha@gmail.com
To: qatestnatasha@gmail.com
Subject: Test Email

Sending from Gmail API
*
* RnJvbTogcWF0ZXN0b
ClN1YmplY3Q6IFR
*
*raw json body
* {
    "raw":"RnJvbTogcWF0ZXN0bmF0YXNoYUBnbWFpbC5jb20KVG86IHFhdGVzdG5hdGFzaGFAZ21haWwuY29tClN1YmplY3Q6IFRlc3QgRW1haWwKClNlbmRpbmcgZnJvbSBHbWFpbCBBUEk="
}
*
* execute
*
* {
    "id": "190bad7d86d55b5a",
    "threadId": "190bad7d86d55b5a",
    "labelIds": [
        "UNREAD",
        "SENT",
        "INBOX"
    ]
}
* Received email on gmail
*
* */

// ------------------------------------------------------------------------------------------------------------------------------------

   /*
 *  Implicit Grant Flow - FE channel only, not secure because the access token is available in the browser
 * only security that the access token is shortly lived/ short expired for java script applications stored in the browser
 *
 * Only difference is in this case there is no auth code that is sent from the auth server back to the client app
 *
 * 1 -> Resource owner
 * 2 -> Application
 * 3 -> Auth server to resource owner
 * 4 -> Access token from auth server to app
 * 5 -> From app access token to resource api retrieve the photo
 *
 *https://developers.google.com/identity/protocols/oauth2/javascript-implicit-flow
 *
 *value for the response type is token
 *
 * https://accounts.google.com/o/oauth2/v2/auth?
 scope=https%3A//www.googleapis.com/auth/drive.metadata.readonly&
 include_granted_scopes=true&
 response_type=token&
 state=state_parameter_passthrough_value&
 redirect_uri=https%3A//oauth2.example.com/code&
 client_id=client_id
 *
 * we are not providing the client secret, all information we are sending are public
 *
 * copy paste the url
 * access_token ya29.a0AXooCgvANI6zC_iO-nw0169
 * no refresh token involved here
 * open request for getUserProfile paste the token
 * execute
 *
 * {
    "emailAddress": "qatestnatasha@gmail.com",
    "messagesTotal": 5,
    "threadsTotal": 5,
    "historyId": "1979"
}
 * */



/*
*   OpenID Connect + OAuth
*
*    https://developers.google.com/identity/openid-connect/openid-connect
*
* https://accounts.google.com/o/oauth2/v2/auth?
 response_type=code&
 client_id=424911365001.apps.googleusercontent.com&
 scope=openid%20email&
 redirect_uri=https%3A//oauth2.example.com/code&
 state=security_token%3D138r5719ru3e1%26url%3Dhttps%3A%2F%2Foauth2-login-demo.example.com%2FmyHome&
 login_hint=jsmith@example.com&
 nonce=0394852-3190485-2490358&
 hd=example.com
 *
 * change params infos execute request
 * https://localhost:8080/?state=security_token%3D138r5719ru3e1%26url%3Dhttps%3A%2F%2Foauth2-login-gmail.com%2FmyHome&code=4%2F0AcvDMrBhkeuvyNpbOt5GDlP1dAyDaUpUoAsjS8OZJFv2p6muDgsUtr1ks099NfRDRMtciQ&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fmail.google.com%2F&authuser=0&prompt=consent
 * code 4%2F0AcvDMrD
 *
 * Paste it in get token and check documentation execute the request we receive invalid error issue with the code because of special characters
 * replace %2F with / 4/0AcvDjtP_KXseAQ
 * ya29.a0AXooCguUOOMJyjo8zrZnwTs-naCgYKAcMSARASFQHGX2Mis302z9r03FipoQjGGZAbcw0171
 *
 * id token is jwt token eyJhbGciOiJSUzI1NiIsImtiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIzNzQzMjI0MjU1NTUtcmQzNWtiNDdmdHYxMDk0cHQwb25sdWVhOWx0bDBnNWguYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIzNzQzMjI0MjU1NTUtcmQzNWtiNDdmdHYxMDk0cHQwb25sdWVhOWx0bDBnNWguYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTc2Mzk5ODc1MDg4MjQyMDQxMDUiLCJlbWFpbCI6InFhdGVzdG5hdGFzaGFAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF0X2hhc2giOiJ3bWh6RE4wUnliYUMxSnB5MWxNX09BIiwibm9uY2UiOiIwMzk0ODUyLTMxOTA0ODUtMjQ5MDM1OCIsImlhdCI6MTcyMTI5MzUwMCwiZXhwIjoxNzIxMjk3MTAwfQ.nyn7JSpRAWHP7bhIOiYJJsSIwknVuBIG4oHRhJ_DTjRweOz0rdttaZL_i67g37GL2x1_xjqA7zOIaL3-OtfCbhAZge_vJNGm9bMlJ68FnKxiimYl5cTj1b8BuH1NEcYxXEgH-P3elZ_NDmPxqyGgaITXWBfmGsqUWQRRoiDqAObsXBzEPpqzZIC-ePhcjsuHuHbGqerGfrn9CROE7fLBjolr9uAR18Qib6QQH4kueuh_2xLCIF32Of1rL6-9S16QvIj1bo_udgoLCQAKSFE4nqWf--q95py-NrFeGaFKpbjHwY9s44TkHijeI5xLvW_HwfaBbso38YvYQVesOiWOrw
 * navigate to https://jwt.io/
 *
 * Decoding the token
 * header info
 * {
  "alg": "RS256",
  "kid": "f2e11986282de93f27b264fd2a4de192993dcb8c",
  "typ": "JWT"
}
*
* payload
*{
  "iss": "https://accounts.google.com",
  "azp": "374322425555-rd35kb47ftv1094pt0onluea9ltl0g5h.apps.googleusercontent.com",
  "aud": "374322425555-rd35kb47ftv1094pt0onluea9ltl0g5h.apps.googleusercontent.com",
  "sub": "117639987508824204105",
  "email": "qatestnatasha@gmail.com",
  "email_verified": true,
  "at_hash": "wmhzDN0RybaC1Jpy1lM_OA",
  "nonce": "0394852-3190485-2490358",
  "iat": 1721293500,
  "exp": 1721297100
}
*
* show me the time when token is created and when ot expires
*azp is the client id
* if the token expires we can get exp to renew the token
*
*verify signature
*
* RSASHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  *
  * {
  "e": "AQAB",
  "kty": "RSA",
  "n": "zaUomGGU1qSBxBHOQRk5fF7rOVVzG5syHhJYociRyyvvMOM6Yx_n7QFrwKxW1Gv-YKPDsvs-ksSN5YsozOTb9Y2HlPsOXrnZHQTQIdjWcfUz-TLDknAdJsK3A0xZvq5ud7ElIrXPFS9UvUrXDbIv5ruv0w4pvkDrp_Xdhw32wakR5z0zmjilOHeEJ73JFoChOaVxoRfpXkFGON5ZTfiCoO9o0piPROLBKUtIg_uzMGzB6znWU8Yfv3UlGjS-ixApSltsXZHLZfat1sUvKmgT03eXV8EmNuMccrhLl5AvqKT6E5UsTheSB0veepQgX8XCEex-P3LCklisnen3UKOtLw"
}
*
* we can use the access toke, id token is primarily used by the client application, just to authenticate the user
*
*
*
* */

/*
*
* https://auth0.com/docs/tokens/json-web-tokens
* What are the three main components of a JWT token?
* https://auth0.com/docs/secure/tokens/json-web-tokens
* */
