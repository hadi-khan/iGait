package com.igaitapp.virtualmd.igait;

import java.io.Serializable;

/**
 * ContactInfo holds things like email, phone number, and address.
 * This can apply to both users and patients.
 */
public class ContactInfo implements Serializable {
    // The contactinfo's necessary info.
    private String email, address, city, state;
    private long phoneNumber, zipCode;

    // Blank constructor. Not really necessary.
    public ContactInfo() {

    }

    // Full constructor.
    public ContactInfo(long phoneNumber, String email, String address, String city, String state, long zipCode) {
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    // The various setters and getters for contactinfo's necessary info.

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getZipCode() {
        return zipCode;
    }

    public void setZipCode(long zipCode) {
        this.zipCode = zipCode;
    }
}
