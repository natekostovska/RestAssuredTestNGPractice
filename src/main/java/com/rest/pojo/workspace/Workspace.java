package com.rest.pojo.workspace;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(value = {"id", "i"}, allowSetters = true)
// provide name of the property we want to ignore, deserialization->allowsetters, serialization->allowgetters
public class Workspace {// AT CLASS LEVEL


    public Workspace() {

    }

//Field Level - different criteria for any field
    //  @JsonInclude(JsonInclude.Include.NON_DEFAULT)

    //   @JsonIgnore
    private int i;
    //   @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnore
    private String id;
    // @JsonInclude(JsonInclude.Include.NON_EMPTY) // will work for list

    @JsonIgnore
    HashMap<String, String> myHashMap;
    private String name;
    private String type;
    private String description;


    public Workspace(String name, String type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }


    public HashMap<String, String> getMyHashMap() {
        return myHashMap;
    }

    public void setMyHashMap(HashMap<String, String> myHashMap) {
        this.myHashMap = myHashMap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}


