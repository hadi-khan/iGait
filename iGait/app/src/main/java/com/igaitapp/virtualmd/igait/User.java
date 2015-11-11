package com.igaitapp.virtualmd.igait;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User implements Serializable {
    private String lastName, firstName;
    private ContactInfo contactInfo = new ContactInfo();

    public User() {

    }

    public User(String lastName, String firstName, ContactInfo contactInfo) {
        super();

        this.lastName = lastName;
        this.firstName = firstName;
        this.contactInfo = contactInfo;
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

}
