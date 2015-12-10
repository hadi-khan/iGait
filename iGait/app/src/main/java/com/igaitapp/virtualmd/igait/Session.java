package com.igaitapp.virtualmd.igait;

public class Session {
    // This holds the user and can be accessed from anywhere.
    public static User user = new User();

    // This holds the user's patient listmap (custom object) and can be accessed anywhere,
    public static PatientListMap patientListMap = new PatientListMap();

     // The various setters and getters for user and patientListMap.

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


