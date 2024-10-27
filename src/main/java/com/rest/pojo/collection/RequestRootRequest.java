package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) //will ignore any new property in the response that is not available
public class RequestRootRequest extends RequestRootBase{
    public RequestRequest getRequest() {
        return request;
    }

    public void setRequest(RequestRequest request) {
        this.request = request;
    }

    RequestRequest request;

    public RequestRootRequest(){

    }

    public RequestRootRequest(String name, RequestRequest request){
        super(name);
        this.request=request;
    }
}
