package com.gfa.users.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {
  Role role = new Role();

  @Test
  void create_role() {
    Role r1 = new Role("role");
    assertEquals("role", r1.getRole());
  }

  @Test
  void add_permission() {
    Role r1 = new Role("role");
    Permission permission_01 = new Permission("do something");
    assertTrue(r1.addPermission(permission_01));
    assertTrue(r1.can(permission_01));
  }

  @Test
  void add_same_permission_twice() {
    Role r1 = new Role("role");
    Permission permission_01 = new Permission("do something");
    r1.addPermission(permission_01);
    assertFalse(r1.addPermission(permission_01));
  }

  @Test
  void remove_permission() {
    Role r1 = new Role("role");
    Permission permission_01 = new Permission("do something");
    r1.addPermission(permission_01);
    assertTrue(r1.removePermission(permission_01));
    assertFalse(r1.removePermission(permission_01));
    assertFalse(r1.can(permission_01));
  }

  @Test
  void can_False_permissions() {
    Role role = new Role("role");
    Permission permission_01 = new Permission("do something");
    Permission permission_02 = new Permission("do something else");
    role.addPermission(permission_01);
    assertFalse(role.can(permission_02));
  }

  @Test
  void can_true_permissions() {
    Role role = new Role("role");
    Permission permission_01 = new Permission("do something");
    role.addPermission(permission_01);
    assertTrue(role.can(permission_01));
  }

  @Test
  void can_false_string() {
    Role role = new Role("role");
    Permission permission_01 = new Permission("do something");
    role.addPermission(permission_01);
    assertFalse(role.can("do something else"));
  }

  @Test
  void can_true_string() {
    Role role = new Role("role");
    Permission permission_01 = new Permission("do something");
    role.addPermission(permission_01);
    assertTrue(role.can("do something"));
  }
}
