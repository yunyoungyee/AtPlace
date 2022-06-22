package com.example.atplace;

public class Address {
    private String addressName;
    private String placeName;

    public Address(String addressName, String placeName) {
        this.addressName = addressName;
        this.placeName = placeName;
    }

    public String getAddressName(){
        return addressName;
    };
    public String getPlaceName(){
        return placeName;
    };

}
