package com.example.hospitalproject.security.controller;

import com.example.hospitalproject.security.validation.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@SpringBootTest
public class MatcherTest {

    public static final List<String> correctNames = List.of(
            "Ye",
            "Danylo",
            "Vlad",
            "Serhii"
    );
    public static final List<String> wrongNames = List.of(
            "ilya",
            "bogDan",
            "PAVLO",
            "T"
    );

    public static final List<String> correctEmails = List.of(
            "username@domain.com",
            "user.name@domain.com",
            "user-name@domain.com",
            "username@domain.co.in",
            "user_name@domain.com",
            "username1@domain.co.in"
    );

    public static final List<String> wrongEmails = List.of(
            "username@.com",
            "user-name@domain.com.",
            ".user.name@domain.com",
            "username.@domain.com",
            "@domain.com",
            "$@domain.co.in"
    );

    public static final List<String> correctPhoneNumbers = List.of(
            "+38096543897",
            "0994356248"
    );

    public static final List<String> wrongPhoneNumbers = List.of(
            "756789h890",
            "-678085468",
            "+380",
            "463739tg9r7"
    );

    public static final List<String> correctPasswords = List.of(
            "chwulcwl%jhclw&F",
            "cheoepofjdhrejh527Adhc+j",
            "FHKJTJTJYDY6788GGCGJK",
            "kwncuio85hw",
            "jchwoctsbclwvxyeldnegxjkfbdbdhskdhds7392bdjw3udh28shdfg"
    );

    public static final List<String> wrongPasswords = List.of(
            "fhje",
            "GKK*)",
            "jchwoctsbclwvxyeldnegxjkfbdbdhekdbejkwegdskdhds7392bdjw3udh28shdfgjchwoctsbclwvxyeldnegxjkfbdbdhekdbejkwegdskdhds7392bdjw3udh28shdfgjchwoctsbclwvxyeldnegxjkfbdbdhekdbejkwegdskdhds7392bdjw3udh28shdfg"
    );


    @Test
    void registrationNameSuccessTest(){
        List<Boolean> resultList = new ArrayList<>();
        correctNames.forEach(n -> resultList.add(Matcher.checkName(n)));
        assertEquals(new ArrayList<>(Collections.nCopies(correctNames.size(), true)), resultList);
    }

    @Test
    void wrongRegistrationNameTest(){
        List<Boolean> resultList = new ArrayList<>();
        wrongNames.forEach(n -> resultList.add(Matcher.checkName(n)));
        assertEquals(new ArrayList<>(Collections.nCopies(wrongNames.size(), false)), resultList);
    }

    @Test
    void registrationEmailSuccessTest(){
        List<Boolean> resultList = new ArrayList<>();
        correctEmails.forEach(n -> resultList.add(Matcher.checkEmail(n)));
        assertEquals(new ArrayList<>(Collections.nCopies(correctEmails.size(), true)), resultList);
    }

    @Test
    void wrongRegistrationEmailTest(){
        List<Boolean> resultList = new ArrayList<>();
        wrongEmails.forEach(n -> resultList.add(Matcher.checkEmail(n)));
        assertEquals(new ArrayList<>(Collections.nCopies(wrongEmails.size(), false)), resultList);
    }

    @Test
    void registrationPhoneNumberSuccessTest(){
        List<Boolean> resultList = new ArrayList<>();
        correctPhoneNumbers.forEach(n -> resultList.add(Matcher.checkPhoneNumber(n)));
        assertEquals(new ArrayList<>(Collections.nCopies(correctPhoneNumbers.size(), true)), resultList);
    }

    @Test
    void wrongRegistrationPhoneNumberTest(){
        List<Boolean> resultList = new ArrayList<>();
        wrongPhoneNumbers.forEach(n -> resultList.add(Matcher.checkPhoneNumber(n)));
        assertEquals(new ArrayList<>(Collections.nCopies(wrongPhoneNumbers.size(), false)), resultList);
    }

    @Test
    void registrationPasswordSuccessTest(){
        List<Boolean> resultList = new ArrayList<>();
        correctPasswords.forEach(n -> resultList.add(Matcher.checkPasswordLength(n)));
        assertEquals(new ArrayList<>(Collections.nCopies(correctPasswords.size(), true)), resultList);
    }

    @Test
    void wrongRegistrationPasswordTest(){
        List<Boolean> resultList = new ArrayList<>();
        wrongPasswords.forEach(n -> resultList.add(Matcher.checkPasswordLength(n)));
        assertEquals(new ArrayList<>(Collections.nCopies(wrongPasswords.size(), false)), resultList);
    }
}
