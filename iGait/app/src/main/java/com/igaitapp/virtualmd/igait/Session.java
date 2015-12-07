package com.igaitapp.virtualmd.igait;

public class Session {
    public static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Session.user = user;
    }
}


