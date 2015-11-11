package com.igaitapp.virtualmd.igait;

import java.util.Date;

public class NewPatient {
    private String lastName, firstName;
    private Date expectedWalkTime, birthday;;
    private char sex;
    private ContactInfo contactInfo;

    public NewPatient() {

    }

    public NewPatient(String lastName, String firstName, Date expectedWalkTime, Date birthday, char sex, ContactInfo contactInfo) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.sex = sex;
        this.contactInfo = contactInfo;
        this.expectedWalkTime = expectedWalkTime;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getExpectedWalkTime() {
        return expectedWalkTime;
    }

    public void setExpectedWalkTime(Date expectedWalkTime) {
        this.expectedWalkTime = expectedWalkTime;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }
}
