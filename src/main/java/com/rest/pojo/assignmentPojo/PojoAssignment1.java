package com.rest.pojo.assignmentPojo;

public class PojoAssignment1 {
    private String name;
    private String username;
    private String email;
    private int id;

    PojoAssignment1Address address;

    public  PojoAssignment1(){

    }

    public PojoAssignment1(String name,String username,String email,PojoAssignment1Address address){
        this.name=name;
        this.username=username;
        this.email=email;
        this.address=address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
