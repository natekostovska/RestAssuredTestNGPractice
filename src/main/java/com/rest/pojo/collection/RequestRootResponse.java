package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) //will ignore any new property in the response that is not available
public class RequestRootResponse extends RequestRootBase {
    public RequestResponse getRequest() {
        return request;
    }

    public void setRequest(RequestResponse request) {
        this.request = request;
    }

    RequestResponse request;

    public RequestRootResponse(){

    }

    public RequestRootResponse(String name, RequestResponse request){
        super(name);
        this.request=request;
    }
}
