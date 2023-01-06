package com.example.hospitalproject.security.config;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Component("Matcher")
public class Matcher {
    private static final String NAME_REGEX = "^[A-Z][a-z]+\\b";
    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_REGEX_LENGTH = "\\b.{8,64}\\b";
    private static final String PHONE_NUMBER_REGEX = "\\+*\\d{6,16}";

    public static boolean checkName(String name){
        return Pattern.matches(NAME_REGEX, name);
    }

    public static boolean checkEmail(String email){
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public static boolean checkPasswordLength(String password){
        return Pattern.matches(PASSWORD_REGEX_LENGTH, password);
    }

    public static boolean checkPhoneNumber(String phoneNumber){
        return Pattern.matches(PHONE_NUMBER_REGEX, phoneNumber);
    }
}
