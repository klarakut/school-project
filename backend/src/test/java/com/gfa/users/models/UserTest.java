package com.gfa.users.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserTest {

  User user;
  Date verifiedAt = new Date(0L);
  Date verificationTokenExpiresAt = new Date(0L);
  Date forgottenPasswordTokenExpiresAt = new Date(0L);
  Date createdAt = new Date(0L);

  @BeforeEach
  public void beforeEach() {

    verifiedAt = new Date(0L);
    verificationTokenExpiresAt = new Date(0L);
    forgottenPasswordTokenExpiresAt = new Date(0L);
    createdAt = new Date(0L);

    user =
        new User(
            1L,
            "Carl",
            "email@email.com",
            "password",
            verifiedAt,
            "12345abvde",
            verificationTokenExpiresAt,
            "abcde12345",
            forgottenPasswordTokenExpiresAt,
            createdAt);
  }

  @Test
  void get_username_returns_the_correct_username() {
    assertEquals("Carl", user.getUsername());
    assertNotEquals("Wrong", user.getUsername());
  }

  @Test
  void get_email_returns_the_correct_email() {
    user.setEmail("email@email.com");
    assertEquals("email@email.com", user.getEmail());
    assertNotEquals("notEmail@email.com", user.getEmail());
  }

  @Test
  void user_email_can_be_set() {
    user.setEmail("email2@email.com");
    assertEquals("email2@email.com", user.getEmail());
    user.setEmail("notEmail@email.com");
    assertNotEquals("email2@email.com", user.getEmail());
  }

  @Test
  void get_password_returns_the_correct_plaintext_password() {
    user.setPassword("pass");
    assertEquals("pass", user.getPassword());
  }

  @Test
  void get_verified_at_returns_the_correct_date_and_time() {
    User user01 = new User();
    assertNull(user01.getVerifiedAt());
    assertNotNull(user.getVerifiedAt());
    assertEquals(verifiedAt, user.getVerifiedAt());
  }

  @Test
  void get_verification_token_returns_the_correct_token() {
    assertEquals("12345abvde", user.getVerificationToken());
    assertNotEquals("token", user.getVerificationToken());
  }

  @Test
  void the_verification_token_can_be_set() {
    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTIzNDU";
    user.setVerificationToken(token);
    assertEquals(token, user.getVerificationToken());
    user.setVerificationToken("car");
    assertNotEquals("token", user.getVerificationToken());
  }

  @Test
  void get_verification_token_expires_at_returns_the_correct_date_and_time() {
    assertEquals(forgottenPasswordTokenExpiresAt, user.getVerificationTokenExpiresAt());
  }

  @Test
  void get_forgotten_password_token_returns_the_correct_token() {
    assertEquals("abcde12345", user.getForgottenPasswordToken());
  }

  @Test
  void the_forgotten_password_token_can_be_set() {
    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTIzNDU";
    user.setForgottenPasswordToken(token);
    assertEquals(token, user.getForgottenPasswordToken());
    user.setForgottenPasswordToken("car");
    assertNotEquals("token", user.getForgottenPasswordToken());
  }

  @Test
  void get_forgotten_password_token_expires_at_returns_the_correct_date_and_time() {
    assertEquals(forgottenPasswordTokenExpiresAt, user.getForgottenPasswordTokenExpiresAt());
  }

  @Test
  void get_created_at_returns_the_correct_date_and_time() {
    assertEquals(verifiedAt, user.getCreatedAt());
  }
}
