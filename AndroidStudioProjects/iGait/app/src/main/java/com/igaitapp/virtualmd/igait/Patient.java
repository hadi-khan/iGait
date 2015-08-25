package com.igaitapp.virtualmd.igait;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jesus on 8/21/15.
 */
public class Patient implements Serializable {
    private long idNumber;
    List<Integer> gaitHealth = new ArrayList<Integer>();
    private String lastName, firstName;
    private int age;

    public Patient(long idNumber, String lastName, String firstName, int age, List<Integer> gaitHealth) {
        super();

        this.idNumber = idNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.gaitHealth = gaitHealth;
    }

    public long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(long idNumber) {
        this.idNumber = idNumber;
    }

    public List<Integer> getGaitHealth() {
        return gaitHealth;
    }

    public void setGaitHealth(List<Integer> gaitHealth) {
        this.gaitHealth = gaitHealth;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
