package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) //will ignore any new property in the response that is not available
public class RequestRequest extends RequestBase{
    private String url;

    public RequestRequest(){

    }

    public RequestRequest(String url, String method, List<Header> header, Body body, String description){
        super(method, header, body, description);
        this.url=url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
