package com.igaitapp.virtualmd.igait;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A user is a person who has an account in the iGait system and has patients.
 */
public class User implements Serializable {
    // The user's necessary info. Password does not appear in the constructors as no code uses password
    //  for the initial declaration of a user.
    private String lastName, firstName, id, token, password;
    private ContactInfo contactInfo = new ContactInfo();

    // A blank constructor. Not really necessary.
    public User() {

    }

    // A constructor for a registered user.
    public User(String lastName, String firstName, ContactInfo contactInfo) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.contactInfo = contactInfo;
    }

    // A constructor for a logged in user. Note the id and token.
    public User(String lastName, String firstName, ContactInfo contactInfo, String id, String token) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.contactInfo = contactInfo;
        this.id = id;
        this.token = token;
    }

    // The various setters and getters for user's necessary info.

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
