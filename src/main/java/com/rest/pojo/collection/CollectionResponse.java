package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) //will ignore any new property in the response that is not available
public class CollectionResponse extends CollectionBase{
    List<FolderResponse> item;

    public CollectionResponse(){

    }

    public CollectionResponse(Info info, List<FolderResponse> item){
        super(info);
        this.item=item;
    }
    public List<FolderResponse> getItem() {
        return item;
    }

    public void setItem(List<FolderResponse> item) {
        this.item = item;
    }

}
