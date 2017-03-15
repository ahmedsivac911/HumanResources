
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zira.humanresources.models;

/**
 *
 * @author Ahmed_S
 */
public class Location {

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }
    private final int id;
    private final String address;
    private final String city;
    private final String country;
    
    public Location(int id, String address, String city, String country){
        this.id = id;
        this.address = address;
        this.city = city;
        this.country = country;
    }
    
    @Override
    public String toString(){
        return String.format("%-10d%-30s%-20s%-20s", this.getId(), this.getAddress(), this.getCity(), this.getCountry());
    }
    
    @Override
    public boolean equals(Object otherObject){
        if(otherObject == null)
            return false;
        if(!Location.class.isAssignableFrom(otherObject.getClass()))
            return false;
        Location otherLocation = (Location) otherObject;
        return this.id == otherLocation.id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.id;
        return hash;
    }
}
