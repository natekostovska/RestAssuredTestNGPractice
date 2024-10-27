package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) //will ignore any new property in the response that is not available
public class RequestResponse extends RequestBase{
    URL url;
    public RequestResponse(){

    }

    public RequestResponse(URL url, String method, List<Header> header, Body body, String description){
        super(method, header, body, description);
        this.url=url;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
