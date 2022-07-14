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
    permission = new Permission("permission");
    role = new Role("role");
    team = new Team("team");
    user = new User("user","xx","x", date = new Date(0L));
  }

  @Test
  void get_name() {
    assertEquals("team", team.getName());
  }

  @Test
  void add_permission() {
    assertFalse(team.can(permission));
    assertTrue(team.addPermission(permission));
    assertTrue(team.can(permission));
  }

  @Test
  void add_twice_permission() {
    team.addPermission(permission);
    assertFalse(team.addPermission(permission));
  }

  @Test
  void remove_permission() {
    team.addPermission(permission);
    assertTrue(team.removePermission(permission));
    assertTrue(team.can(permission));
    assertFalse(team.removePermission(permission));
  }

  @Test
  void add_user() {
    assertTrue(team.addUser(user));
  }

  @Test
  void add_twice_user() {
    assertTrue(team.addUser(user));
    assertFalse(team.addUser(user));
  }

  @Test
  void remove_user(){
    assertFalse(team.removeUser(user));
    assertTrue(team.addUser(user));
    assertTrue(team.removeUser(user));
    assertFalse(team.removeUser(user));
  }

  @Test
  void add_role() {
    assertTrue(team.addRole(role));
  }

  @Test
  void add_twice_role() {
    assertTrue(team.addRole(role));
    assertFalse(team.addRole(role));
  }

  @Test
  void remove_role() {
    assertFalse(team.removeRole(role));
    assertTrue(team.addRole(role));
    assertTrue(team.removeRole(role));
    assertFalse(team.removeRole(role));
  }

  @Test
  void can_string() {
    assertFalse(team.can("permission"));
    team.addPermission(permission);
    assertTrue(team.can("permission"));
    team.removePermission(permission);
    role.addPermission(permission);
    assertFalse(team.can("permission"));
    team.addRole(role);
    assertTrue(team.can("permission"));
  }

  @Test
  void can_permission() {
    assertFalse(team.can(permission));
    team.addPermission(permission);
    assertTrue(team.can(permission));
    team.removePermission(permission);
    role.addPermission(permission);
    assertFalse(team.can(permission));
    team.addRole(role);
    assertTrue(team.can(permission));
  }
}
