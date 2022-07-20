package com.gfa.common.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.gfa.common.exceptions.InvalidEmailException;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class EmailValidatorTest {

  @ParameterizedTest(name = "#{index} - Run test with email = {0}")
  @MethodSource("validEmailProvider")
  void test_email_valid(String email) {
    assertDoesNotThrow(() -> EmailValidator.validate(email));
  }

  @ParameterizedTest(name = "#{index} - Run test with email = {0}")
  @MethodSource("invalidEmailProvider")
  void test_email_invalid(String email) {
    assertThrows(InvalidEmailException.class, () -> EmailValidator.validate(email));
  }

  // Valid email addresses
  static Stream<String> validEmailProvider() {
    return Stream.of(
        "hello@example.com", // simple
        "hello@example.co.uk", // .co.uk
        "hello-.+_=#|@example.com", // special characters
        "h@example.com", // local-part one letter
        "h@com", // domain one letter
        "我買@屋企.香港" // unicode, chinese characters
        );
  }

  // Invalid email addresses
  static Stream<String> invalidEmailProvider() {
    return Stream.of(
        "hello", // email need at least one @
        "hello@ " // domain can not end with space (whitespace)
        );
  }
}
