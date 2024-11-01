package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) //will ignore any new property in the response that is not available
public class RequestBase {
     private String method;
    List<Header> header;
    Body body;

    private String description;

    public RequestBase(){

    }

    public RequestBase(String method, List<Header> header, Body body, String description){
         this.method=method;
         this.header=header;
         this.body=body;
         this.description=description;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Header> getHeader() {
        return header;
    }

    public void setHeader(List<Header> header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
