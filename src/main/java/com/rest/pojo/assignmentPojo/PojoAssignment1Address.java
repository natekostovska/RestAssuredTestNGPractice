package com.rest.pojo.assignmentPojo;

public class PojoAssignment1Address {
    private String street;
    private String suite;
    private String city;
    private String zipcode;

    PojoAssignment1Geo geo;
    public PojoAssignment1Address(){

    }

    public PojoAssignment1Address(String street,String suite,String city,String zipcode, PojoAssignment1Geo geo){
        this.street=street;
        this.suite=suite;
        this.city=city;
        this.zipcode=zipcode;
        this.geo=geo;
    }

    public PojoAssignment1Address(PojoAssignment1 pojoAssignment1){

    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
