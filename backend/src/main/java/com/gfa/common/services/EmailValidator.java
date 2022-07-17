package com.gfa.common.services;

import com.gfa.common.exceptions.InvalidEmailException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator { // implements Predicate<String> {

  private static final String EMAIL_PATTERN = "^(.+)@(\\S+)$";

  private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

  public static void validate(final String email) {
    Matcher matcher = pattern.matcher(email);
    if (matcher.matches()) {
      return;
    }
    throw new InvalidEmailException();
  }
}
