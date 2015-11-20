package com.igaitapp.virtualmd.igait;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class InputCheck {
    public static boolean name(String input) {

        return input.length() > 0 && input.length() <= 20;
    }

    public static boolean email(String input) {

        return input.length() > 0 && input.length() <= 75;
    }

    public static boolean phoneNumber(String input) {

        return input.replaceAll("\\D+", "").length() == 10;
    }

    public static boolean expectedWalkTime(String input) {
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");
        boolean result;

        try {
            tf.parse(input);
            result = true;
        } catch (ParseException pe) {
            result = false;
        }

        return result;
    }

    public static boolean birthday(String input) {
        String dfs[] = {"MM/dd/yyyy", "MM-dd-yyyy", "MM.dd.yyyy"};
        boolean result = false;

        for (String df : dfs) {
            try {
                new SimpleDateFormat(df).parse(input);
                result = true || result;
            } catch (ParseException pe) {
                result = false || result;
            }
        }

        return result;
    }

    public static boolean sex(String input) {

        return input.toLowerCase().charAt(0) == 'm' || input.toLowerCase().charAt(0) == 'f';
    }

    public static boolean address(String input) {

        return input.length() > 0 && input.length() <= 30;
    }

    public static boolean city(String input) {

        return input.length() > 0 && input.length() <= 30;
    }

    public static boolean state(String input) {

        return input.length() == 2;
    }

    public static boolean zipCode(String input) {
        boolean result = true;

        if (input.length() == 5) {
            for (int i = 0; i < input.length(); i++) {
                result = Character.isDigit(input.charAt(i)) && result;
            }
        } else {
            result = false;
        }

        return result;
    }

    public static boolean password(String input) {
        return input.length() > 0 && input.length() <= 20;
    }

    public static boolean unChangedPassword(String input) {
        return input.length() == 0;
    }

    public static boolean newPassword(String input) {
        boolean result0 = false, result1 = false;

        if (input.length() >= 5 && input.length() <= 20) {
            for (int i = 0; i < input.length(); i++) {
                result0 = Character.isUpperCase(input.charAt(i)) || result0;
                result1 = Character.isDigit(input.charAt(i)) || result1;
            }
        } else {
            result0 = false;
            result1 = false;
        }

        return result0 && result1;
    }

    public static boolean rePassword(String input0, String input1) {

        return input0.equals(input1);
    }

    public static boolean searchQueryName(String input) {

        return input.length() > 0 && input.length() <= 20;
    }

    public static boolean searchQueryAge(String input) {

        return input.length() > 0 && input.length() <= 3;
    }

    public static boolean noChanges(List<String> input0, List<String> input1) {
        boolean result = true;

        for (int i = 0; i < input0.size(); i++) {
            result = result && input0.get(i).equals(input1.get(i));
        }

        return result;
    }
}