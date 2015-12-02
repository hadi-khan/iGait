package com.igaitapp.virtualmd.igait;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Patient implements Serializable {
    private String lastName, firstName, id;
    private Date birthday, expectedWalkTime;;
    private char sex;
    private ContactInfo contactInfo = new ContactInfo();
    private List<GaitHealth> gaitHealth = new ArrayList<GaitHealth>();
    private boolean priority;

    public Patient() {

    }

    public Patient(String lastName, String firstName, Date expectedWalkTime, Date birthday, char sex, ContactInfo contactInfo, boolean priority) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.expectedWalkTime = expectedWalkTime;
        this.birthday = birthday;
        this.sex = sex;
        this.contactInfo = contactInfo;
        this.priority = priority;
    }

    public Patient(String lastName, String firstName, Date expectedWalkTime, Date birthday, char sex, ContactInfo contactInfo, List<GaitHealth> gaitHealth, boolean priority) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.expectedWalkTime = expectedWalkTime;
        this.birthday = birthday;
        this.sex = sex;
        this.contactInfo = contactInfo;
        this.gaitHealth = gaitHealth;
        this.priority = priority;
    }

    public Patient(String lastName, String firstName, Date expectedWalkTime, Date birthday, char sex, ContactInfo contactInfo, List<GaitHealth> gaitHealth, boolean priority, String id) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.expectedWalkTime = expectedWalkTime;
        this.birthday = birthday;
        this.sex = sex;
        this.contactInfo = contactInfo;
        this.gaitHealth = gaitHealth;
        this.priority = priority;
        this.id = id;
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

    public List<GaitHealth> getGaitHealth() {
        return gaitHealth;
    }

    public void setGaitHealth(List<GaitHealth> gaitHealth) {
        this.gaitHealth = gaitHealth;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }
}
