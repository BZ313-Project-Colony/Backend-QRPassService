package com.group1.QRPass.core.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;
@Component
public class EmailValidation {
    private static final String regexPattern =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    public static boolean patternMatches(String emailAddress) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
}
