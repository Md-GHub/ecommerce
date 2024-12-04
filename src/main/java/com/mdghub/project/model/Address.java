package com.mdghub.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    @NotBlank
    @Size(min = 4, message = "Street name must be atleast 4")
    private String street;
    @NotBlank
    @Size(min = 4, message = "building name must be atleast 4")
    private String buildingName;
    @NotBlank
    @Size(min = 4, message = "city name must be atleast 4")
    private String city;
    @NotBlank
    @Size(min = 4, message = "state name must be atleast 4")
    private String state;
    @NotBlank
    @Size(min = 4, message = "country name must be atleast 4")
    private String country;
    @NotBlank
    @Size(min = 6, message = "pincode name must be atleast 6")
    private String pincode;

    @ManyToMany(mappedBy = "address")
    private List<Users> user = new ArrayList<>();


    public Address(String street, String buildingName, String city,
                   String state, String country, String pincode) {
        this.street = street;
        this.buildingName = buildingName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
    }

    public Address() {

    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public List<Users> getUser() {
        return user;
    }

    public void setUser(List<Users> user) {
        this.user = user;
    }
}
