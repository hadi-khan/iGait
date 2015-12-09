package com.igaitapp.virtualmd.igait;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User implements Serializable {
    private String lastName, firstName, id, token, password;
    private ContactInfo contactInfo = new ContactInfo();

    public User() {

    }

    public User(String lastName, String firstName, ContactInfo contactInfo) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.contactInfo = contactInfo;
    }

    public User(String lastName, String firstName, ContactInfo contactInfo, String id, String token) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.contactInfo = contactInfo;
        this.id = id;
        this.token = token;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
