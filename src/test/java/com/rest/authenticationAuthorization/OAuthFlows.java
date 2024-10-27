package com.rest.authenticationAuthorization;

public class OAuthFlows {
     /*
     *  Resource is something that is sought in the OAuth flow, so it is a protected resource
     * In the example it is the photo that is present on the Google photos application. Since it is protected, not everyone has access to it
     * The client application can get access to the protected resource (oauth)
     *
     *   1 - Resource owner is the person or entity that has got access to the protected resource
     * In this case the user that is using the application as well as the google photos app
     *
     * 4- Auth Code/ Resource server something that holds the protected resource (the photo). It is hosting the photo that belongs to the
     * resource owner
     *
     * 2 /5-  Client app looking for access to the protected resource that is hosted on the resource server. Will be requesting for the
     * resource on behalf of the resource owner and its authorization
     *
     * 3 -  Authorization server is coupled with the resource server. It could be the same or separate server
     * Main purpose is to issue the access token to the client
     * */

    /*
     *  Authorization Grant Flow -> BE channel safest
     *
     * 1 -> Resource owner
     * 2 -> Application
     * 3 -> Auth server to resource owner
     * 4 -> Auth Code from authorization server to the app
     * 5 -> From app Refresh token/access token is valid until user is logged in the browser until login session is active
     *  to the authorization server
     * Once token is expired the client app can use refresh token to renew access token as lon as the refresh token is valid
     * 6 -> From app access token to resource api retrieve the photo
     * */

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
     * */

    /*
     *  Client Credentials Flow - no user involved there are only services
     *
     * One service can act as the client and another as the auth server. Microservice architecture (all services developed by one architect)
     *
     * 1 -> Service 1 implements two different APIs (gives user profile info like the user mail etc, the second info about user activities)
     * 2 -> Service 2 want to leverage the api that gives info on the user activities, not the one that gives info on the user profile (service 2 does not have access to this)
     * 3 -> Auth server - gives info on the user activities to service 2
     *
     *  Service 2 sends the client id and secret to the authorization server. Client id is unique to the service (public), client secret is private
     * to the service
     *  The authorization service validates this info, validates that the request is coming from the correct client
     *  After validation it sends back access token to the client to service 2 with expire time 5,15,30 minutes
     *  Service 2 will hold the access token and will send it to service 1 to get access on the api that gives info on the user activities
     *  Send back service 1 to 2 the requested info (resource) validates the token if its not correct will reject
     * */
}
