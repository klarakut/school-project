package com.gfa.users.services;

import org.springframework.stereotype.Service;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailValidator { //implements Predicate<String> {


    private static final String EMAIL_PATTERN = "^(.+)@(\\S+)$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isValid(final String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
