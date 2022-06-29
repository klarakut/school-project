package com.gfa.users.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

  User user;
  Date verifiedAt = new Date(0L);
  Date verificationTokenExpiresAt = new Date(0L);
  Date forgottenPasswordTokenExpiresAt = new Date(0L);
  Date createdAt = new Date(0L);

  @BeforeEach
  public void beforeEach(){

    verifiedAt = new Date(0L);
    verificationTokenExpiresAt = new Date(0L);
    forgottenPasswordTokenExpiresAt = new Date(0L);
    createdAt = new Date(0L);

    user = new User(1L,"Carl","email@email.com","password",verifiedAt,
            "12345abvde",verificationTokenExpiresAt,
            "abcde12345",forgottenPasswordTokenExpiresAt,createdAt);
  }

  @Test
  void getUsername() {
    assertEquals("Carl", user.getUsername());
    assertNotEquals("Wrong", user.getUsername());
  }

  @Test
  void getEmail() {
    user.setEmail("email@email.com");
    assertEquals("email@email.com", user.getEmail());
    assertNotEquals("notEmail@email.com", user.getEmail());
  }

  @Test
  void setEmail() {
    user.setEmail("email2@email.com");
    assertEquals("email2@email.com", user.getEmail());
    user.setEmail("notEmail@email.com");
    assertNotEquals("email2@email.com", user.getEmail());
  }

  @Test
  void getId() {
    user.setId(15L);
    assertEquals(15L, user.getId());
    assertNotEquals(25L, user.getId());
  }

  @Test
  void setId() {
    user.setId(15L);
    assertEquals(15L, user.getId());
    user.setId(92L);
    assertNotEquals(15L, user.getId());
  }

  @Test
  void setUsername() {
    user.setUsername("Carl");
    assertEquals("Carl", user.getUsername());
    user.setUsername("Michael");
    assertNotEquals("Carl", user.getUsername());
  }

  @Test
  void getPassword() {
    user.setPassword("pass");
    assertTrue(BCrypt.checkpw("pass", user.getPassword()));
    assertFalse(BCrypt.checkpw("pass2", user.getPassword()));
  }

  @Test
  void setPassword() {
    String pass = BCrypt.hashpw("earth", BCrypt.gensalt(12));
    assertTrue(BCrypt.checkpw("earth", pass));
    assertFalse(BCrypt.checkpw("null", pass));
  }

  @Test
  void getVerified_at() {
    User user01 = new User();
    assertNull(user01.getVerifiedAt());
    assertNotNull(user.getVerifiedAt());
    assertEquals(verifiedAt, user.getVerifiedAt());
  }

  @Test
  void getVerification_token() {
    assertEquals("12345abvde", user.getVerificationToken());
    assertNotEquals("token", user.getVerificationToken());
  }

  @Test
  void setVerification_token() {
    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTIzNDU";
    user.setVerificationToken(token);
    assertEquals(token, user.getVerificationToken());
    user.setVerificationToken("car");
    assertNotEquals("token", user.getVerificationToken());
  }

  @Test
  void getVerification_token_expires_at() {
    assertEquals(forgottenPasswordTokenExpiresAt, user.getVerificationTokenExpiresAt());
  }

  @Test
  void getForgotten_password_token() {
    assertEquals("abcde12345", user.getForgottenPasswordToken());
  }

  @Test
  void setForgotten_password_token() {
    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTIzNDU";
    user.setForgottenPasswordToken(token);
    assertEquals(token, user.getForgottenPasswordToken());
    user.setForgottenPasswordToken("car");
    assertNotEquals("token", user.getForgottenPasswordToken());
  }

  @Test
  void getForgotten_password_token_expires_at() {
    assertEquals(forgottenPasswordTokenExpiresAt, user.getForgottenPasswordTokenExpiresAt());
  }

  @Test
  void getCreated_at() {
    assertEquals(verifiedAt, user.getCreatedAt());
  }

  @Test
  void strToDate() throws ParseException {
    String strDate = "2022-12-25 18:55:13";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = simpleDateFormat.parse(strDate);
    assertEquals(date,user.strToDate(strDate));
  }
}
