package com.igaitapp.virtualmd.igait;

import java.util.ArrayList;
import java.util.List;

public class Session {
    public static User user = new User();
    public static PatientListMap patientListMap = new PatientListMap();

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Session.user = user;
    }

    public static PatientListMap getPatientListMap() {
        return patientListMap;
    }

    public static void setPatientListMap(PatientListMap patientListMap) {
        Session.patientListMap = patientListMap;
    }
}


