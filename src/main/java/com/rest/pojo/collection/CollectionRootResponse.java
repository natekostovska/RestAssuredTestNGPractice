package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) //will ignore any new property in the response that is not available
public class CollectionRootResponse extends CollectionRootBase{
public CollectionResponse getCollection() {
        return collection;
    }

    public void setCollection(CollectionResponse collection) {
        this.collection = collection;
    }

    CollectionResponse collection;

    public CollectionRootResponse(){

    }

    public CollectionRootResponse(CollectionResponse collection){
        this.collection=collection;
    }

}
