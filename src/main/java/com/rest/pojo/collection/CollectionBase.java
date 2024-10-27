package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) //will ignore any new property in the response that is not available

public class CollectionBase {
     Info info;

    public CollectionBase(){

    }

    public CollectionBase(Info info){
        this.info=info;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
