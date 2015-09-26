package com.igaitapp.virtualmd.igait;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jesus on 8/21/15.
 */
public class Patient implements Serializable {
    private long idNumber;
    private String lastName, firstName;
    private Date birthday, expected;
    private List<GaitHealth> gaitHealth = new ArrayList<GaitHealth>();
    private boolean priority;

    public Patient(long idNumber, String lastName, String firstName, Date birthday,  Date expected, List<GaitHealth> gaitHealth, boolean priority) {
        super();

        this.idNumber = idNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.expected = expected;
        this.gaitHealth = gaitHealth;
        this.priority = priority;
    }

    public long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(long idNumber) {
        this.idNumber = idNumber;
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

    public Date getExpected() {
        return expected;
    }

    public void setExpected(Date expected) {
        this.expected = expected;
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
