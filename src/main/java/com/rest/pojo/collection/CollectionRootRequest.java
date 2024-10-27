package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) //will ignore any new property in the response that is not available
public class CollectionRootRequest extends CollectionRootBase {
    public CollectionRequest getCollection() {
        return collection;
    }

    public void setCollection(CollectionRequest collection) {
        this.collection = collection;
    }

    CollectionRequest collection;

    public CollectionRootRequest(){

    }

    public CollectionRootRequest(CollectionRequest collection){
        this.collection=collection;
    }

}
