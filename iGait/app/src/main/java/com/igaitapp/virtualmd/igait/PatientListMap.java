package com.igaitapp.virtualmd.igait;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A combination between a list and a hash map.
 * Created in order to access patient's by element (0,1, 2, 3), as well
 *  as key (patient id).
 */
public class PatientListMap {
    List<Patient> patientList = new ArrayList<>();
    HashMap<String, Patient> patientMap = new HashMap<>();

    public boolean isEmpty() {
        return patientList.isEmpty();
    }

    public void add(Patient patient) {
        patientList.add(patient);
        patientMap.put(patient.getId(), patient);
    }

    public Patient get(int i) {
        return patientList.get(i);
    }

    public Patient getUsingKey(String k) {
        return patientMap.get(k);
    }

    public List<Patient> getList() {
        return patientList;
    }

    public void remove(int i) {
        String id = patientList.get(i).getId();

        patientList.remove(i);
        patientMap.remove(id);
    }

    public void removeUsingKey(String k) {
        patientMap.remove(k);

        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getId().equals(k)) {
                patientList.remove(i);
                break;
            }
        }
    }

    public void replace(int i, Patient patient) {
        String id = patient.getId();

        patientList.set(i, patient);
        patientMap.put(id, patient);
    }

    public void replaceUsingKey(String k, Patient patient) {
        patientMap.put(k, patient);

        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getId().equals(k)) {
                patientList.set(i, patient);
                break;
            }
        }
    }
}
