package com.rest.serializationDeserialization;

public class sAndDInJava {
     /* With Java
    The Java object (java class) it is converted into byte stream process called as serialization.
    Then the byte stream can be stored in a file, DB or in a memory.
    Then the byte stream is fetched and added to a java object process called de-serialization
    We are doing this because the byte stream is independent of the platform. So it can be used anywhere
    by any platform. If its Java Object it is restricted by java programming language. If its byte stream can be used in
    any programming language. Byte stream represents the data, returns the data, so that is the advantage

    In Rest Assured
    The Java object can be a POJO class, Map or a List. It is converted to JSON or XML Object and the process is called
    serialization, and it is done by different libraries for ex. Jackson or Gson if its JSON object and JAXB if its XML object.
    These libraries are called as serializers. Once the Java object is serialized into Json or XML object then is sent
    as the request payload as part of the Http request that goes to the server. The server processes the request and sends it back
    the HTTP Response.
    The HTTP Response might have a response body, which can be a JSON or XML Object. And this JSON or XML Object is converted back
    to Java object and the process is called deserialization.
    The process of serialization and deserialization is called object mapping
    Serialization and deserialization is done internally by rest assured, and it uses one of the libraries under the hood to do the job
    All we need to do is to create a Java object
    * */
}
