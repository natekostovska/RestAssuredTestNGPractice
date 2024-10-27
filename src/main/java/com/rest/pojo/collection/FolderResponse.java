package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) //will ignore any new property in the response that is not available
public class FolderResponse extends FolderBase{

    List<RequestRootResponse> item;

    public FolderResponse(){

    }

    public FolderResponse(String name, List<RequestRootResponse> item){
        super(name);
        this.item =item;
    }

    public List<RequestRootResponse> getItem() {
        return item;
    }

    public void setItem(List<RequestRootResponse> item) {
        this.item = item;
    }

}
