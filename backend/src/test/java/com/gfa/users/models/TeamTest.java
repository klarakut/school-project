package com.gfa.users.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

  Permission permission;
  Role role;
  Team team;
  User user;
  Date date;

  @BeforeEach
  public void beforeEach() {
    Permission permission = new Permission("permission");
    Role role = new Role("role");
    Team team = new Team("team");
    User user = new User("user","xx","x", new Date());
  }

  @Test
  void getName() {
    assertEquals("team", team.getName());
  }

  @Test
  void addPermission() {
    assertFalse(team.can(permission))
    assertTrue(team.addPermission(permission));
    assertTrue(team.can(permission));
  }

  @Test
  void addTwicePermission() {
    team.addPermission(permission);
    assertFalse(team.addPermission(permission));
  }

  @Test
  void removePermission() {
    team.addPermission(permission);
    assertTrue(team.removePermission(permission));
    assertTrue(team.can(permission));
    assertFalse(team.removePermission(permission));
  }

  @Test
  void addUser() {
    assertTrue(team.addUser(user));
  }

  @Test
  void addTwiceUser() {
    assertTrue(team.addUser(user));
    assertFalse(team.addUser(user));
  }

  @Test
  void removeUser(){
    assertFalse(team.removeUser(user));
    assertTrue(team.addUser(user));
    assertTrue(team.removeUser(user));
    assertFalse(team.removeUser(user));
  }

  @Test
  void addRole() {
    assertTrue(team.addRole(role));
  }

  @Test
  void addTwiceRole() {
    assertTrue(team.addRole(role));
    assertFalse(team.addRole(role));
  }

  @Test
  void removeRole() {
    assertFalse(team.removeRole(role));
    assertTrue(team.addRole(role));
    assertTrue(team.removeRole(role));
    assertFalse(team.removeRole(role));
  }
}
