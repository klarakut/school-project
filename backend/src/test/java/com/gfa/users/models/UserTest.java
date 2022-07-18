package com.gfa.users.models;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;


class UserTest {

  Team team;
  Role role;
  Permission permission;
  Permission per;
  Permission mission;
  User user;
  LocalDateTime verifiedAt;
  LocalDateTime verificationTokenExpiresAt;
  LocalDateTime forgottenPasswordTokenExpiresAt;
  LocalDateTime createdAt;

  @BeforeEach
  public void beforeEach() {
    team = new Team("team");
    role = new Role("role");
    permission = new Permission("permission");
    per = new Permission("per");
    mission = new Permission("mission");
    verifiedAt = LocalDateTime.of(2021, Month.APRIL, 24, 14,30);
    verificationTokenExpiresAt = LocalDateTime.of(2021, Month.APRIL, 24, 14,30);
    forgottenPasswordTokenExpiresAt = LocalDateTime.of(2021, Month.APRIL, 24, 14,30);
    createdAt = LocalDateTime.now();
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
    assertEquals(createdAt, user.getCreatedAt());
  }

  @Test
  void can_is_true_for_added_permission() {
    assertFalse(user.can(permission));
    assertTrue(user.addPermission(permission));
    assertTrue(user.can(permission));
  }

  @Test
  void can_is_false_for_add_same_permission_twice() {
    user.addPermission(permission);
    assertFalse(user.addPermission(permission));
  }

  @Test
  void can_remove_added_permission() {
    user.addPermission(permission);
    assertTrue(user.removePermission(permission));
    assertFalse(user.can(permission));
    assertFalse(user.removePermission(permission));
  }

  @Test
  void add_role_is_true() {
    assertTrue(user.addRole(role));
  }

  @Test
  void add_role_is_false_for_add_same_role_twice() {
    assertTrue(user.addRole(role));
    assertFalse(user.addRole(role));
  }

  @Test
  void can_remove_added_role() {
    assertFalse(user.removeRole(role));
    assertTrue(user.addRole(role));
    assertTrue(user.removeRole(role));
    assertFalse(user.removeRole(role));
  }

  @Test
  void add_team_is_true() {
    assertTrue(user.addTeam(team));
  }

  @Test
  void add_team_is_false_for_add_same_team_twice() {
    assertTrue(user.addTeam(team));
    assertFalse(user.addTeam(team));
  }

  @Test
  void can_remove_added_team() {
    assertFalse(user.removeTeam(team));
    assertTrue(user.addTeam(team));
    assertTrue(user.removeTeam(team));
    assertFalse(user.removeTeam(team));
  }

  @Test
  void can_is_true_through_TEAM_ROLE_USER_classes_with_permission() {
    user.addRole(role);
    user.addTeam(team);
    assertFalse(user.can(permission));
    assertTrue(user.addPermission(permission));
    assertTrue(user.can(permission));
    assertFalse(user.can(per));
    role.addPermission(per);
    assertTrue(user.can(per));
    assertFalse(user.can(mission));
    team.addPermission(mission);
    assertTrue(user.can(mission));
  }

  @Test
  void can_is_true_through_TEAM_ROLE_USER_classes_with_string() {
    user.addRole(role);
    user.addTeam(team);
    assertFalse(user.can("permission"));
    assertTrue(user.addPermission(permission));
    assertTrue(user.can("permission"));
    assertFalse(user.can("per"));
    role.addPermission(per);
    assertTrue(user.can("per"));
    assertFalse(user.can("mission"));
    team.addPermission(mission);
    assertTrue(user.can("mission"));
  }


}
