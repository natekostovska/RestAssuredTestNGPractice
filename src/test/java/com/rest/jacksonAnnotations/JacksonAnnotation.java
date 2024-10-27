package com.rest.jacksonAnnotations;

public class JacksonAnnotation {
      /*
    *
    *  with serialization id=null, after deserialization it has value
    *  If we navigate to the pojo class String id is not set, it will use the default value of String that is null
    * We can set the annotation at class or property level
    * Workspace and POJOSandD2 classes
    *
    * @JsonInclude(JsonInclude.Include.NON_NULL) will include only those properties with value as not null, will ignore id
    *
    * Body:
{
    "workspace": {
        "name": "myWorkspace4",
        "type": "personal",
        "description": "description"
    }
}
*
*   If i add int i and add get and set execute the request it will give me only those properties with value as not null, will ignore id
*  and add i=0 as default value
* Body:
{
    "workspace": {
        "i": 0,
        "name": "myWorkspace4",
        "type": "personal",
        "description": "description"
    }
}
*
* ----------------------------------------------------------------------------------------------------------------------
* @JsonInclude(JsonInclude.Include.NON_DEFAULT) will ignore default values
* both fields will be ignored
*
*Body:
{
    "workspace": {
        "name": "myWorkspace4",
        "type": "personal",
        "description": "description"
    }
}
*
* // AT CLASS LEVEL
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonInclude(JsonInclude.Include.NON_DEFAULT)

public class Workspace {
    public Workspace(){

    }

//Field Level - different criteria for any field
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int i;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
*
*
* --------------------------------------------------------------------------------------------------------------------------------
*  Adding empty hash map
*{
    "workspace": {
        "myHashMap": {

        },
        "name": "myWorkspace4",
        "type": "personal",
        "description": "description"
    }
}
*
*  @JsonInclude(JsonInclude.Include.NON_EMPTY) // will work for list
    HashMap<String,String> myHashMap;
    *
    * Body:
{
    "workspace": {
        "name": "myWorkspace4",
        "type": "personal",
        "description": "description"
    }
}
    *
* --------------------------------------------------------------------------------------------------------------
* JSON Ignore will ignore the property during both serialization and deserialization
* both HashMap and integer i are not used anywhere, so we can ignore them
*It will ignore the entire property no mather if we use it for the method or at field level for both serialization and deserialization
*
*  @JsonIgnore
* public int getI() {
        return i;
    }
    *
    *
    *     @JsonIgnore
    private int i;
    *
    *
    *
    * {
    "workspace": {
        "name": "myWorkspace4",
        "type": "personal",
        "description": "description"
    }
}
*
* if we do the same for id will be ok for the serialization but not for the deserialization because it is used in the response body
*
* Body:
{
    "workspace": {
        "name": "myWorkspace4",
        "type": "personal",
        "description": "description"
    }
}
HTTP/1.1 200


{
    "workspace": {
        "id": "7c4be98b-a9e7-425a-a8d2-cea0d55dd57f",
        "name": "myWorkspace4"
    }
}
HTTP/1.1 200


{
    "workspace": {
        "id": "7c4be98b-a9e7-425a-a8d2-cea0d55dd57f",
        "name": "myWorkspace4"
    }
}

java.lang.AssertionError:
Expected: a string matching the pattern '^[a-z0-9-]{36}$'
     but: was null
     *
     *
     * So we want to only be ignored during serialization id so it cannot be done with JsonIgnore annotation

* -----------------------------------------------------------------------------------------------------------------
*
*@JsonIgnoreProperties(value = "id",allowSetters = true) // provide name of the property we want to ignore,
*  deserialization->allowsetters, serialization->allowgetters
     *
     * @JsonIgnoreProperties(value = {"id","i"},allowSetters = true) multiple properties
*
    * */

}
