package com.rest.pojo.assignmentPojo;

public class PojoAssignment1Geo {

    private String lat;
    private String lng;

    public  PojoAssignment1Geo(PojoAssignment1Address pojoAssignment1Address){

    }

    public PojoAssignment1Geo(String lat,String lng){
        this.lat=lat;
        this.lng=lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
