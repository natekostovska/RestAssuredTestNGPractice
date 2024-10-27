package com.rest.pojo.assignmentPojo;

public class PojoAssignmentRoot {
    PojoAssignment1 pojoAssignment1;
    PojoAssignment1Address pojoAssignment1Address;
    PojoAssignment1Geo pojoAssignment1Geo;

    public  PojoAssignmentRoot(){

    }

    public  PojoAssignmentRoot(PojoAssignment1 pojoAssignment1, PojoAssignment1Address pojoAssignment1Address,PojoAssignment1Geo pojoAssignment1Geo){
        this.pojoAssignment1=pojoAssignment1;
        this.pojoAssignment1Address=pojoAssignment1Address;
        this.pojoAssignment1Geo=pojoAssignment1Geo;
    }

    public PojoAssignment1 getPojoAssignment1() {
        return pojoAssignment1;
    }

    public void setPojoAssignment1(PojoAssignment1 pojoAssignment1) {
        this.pojoAssignment1 = pojoAssignment1;
    }

    public PojoAssignment1Address getPojoAssignment1Address() {
        return pojoAssignment1Address;
    }

    public void setPojoAssignment1Address(PojoAssignment1Address pojoAssignment1Address) {
        this.pojoAssignment1Address = pojoAssignment1Address;
    }

    public PojoAssignment1Geo getPojoAssignment1Geo() {
        return pojoAssignment1Geo;
    }

    public void setPojoAssignment1Geo(PojoAssignment1Geo pojoAssignment1Geo) {
        this.pojoAssignment1Geo = pojoAssignment1Geo;
    }
}
