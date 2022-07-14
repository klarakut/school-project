package com.gfa.users.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {
  Role role = new Role();

  @Test
  void createRole() {
    Role r1 = new Role("role");
    assertEquals("role", r1.getRole());
  }

  @Test
  void addPermission() {
    Role r1 = new Role("role");
    Permission permission_01 = new Permission("do something");
    assertTrue(r1.addPermission(permission_01));
    assertTrue(r1.can(permission_01));
  }

  @Test
  void addSamePermissionTwice() {
    Role r1 = new Role("role");
    Permission permission_01 = new Permission("do something");
    r1.addPermission(permission_01);
    assertFalse(r1.addPermission(permission_01));
  }

  @Test
  void removePermission() {
    Role r1 = new Role("role");
    Permission permission_01 = new Permission("do something");
    r1.addPermission(permission_01);
    assertTrue(r1.removePermission(permission_01));
    assertFalse(r1.removePermission(permission_01));
    assertFalse(r1.can(permission_01));
  }

  @Test
  void canFalsePermissions() {
    Role role = new Role("role");
    Permission permission_01 = new Permission("do something");
    Permission permission_02 = new Permission("do something else");
    role.addPermission(permission_01);
    assertFalse(role.can(permission_02));
  }

  @Test
  void canTruePermissions() {
    Role role = new Role("role");
    Permission permission_01 = new Permission("do something");
    role.addPermission(permission_01);
    assertTrue(role.can(permission_01));
  }

  @Test
  void canFalseString() {
    Role role = new Role("role");
    Permission permission_01 = new Permission("do something");
    role.addPermission(permission_01);
    assertFalse(role.can("do something else"));
  }

  @Test
  void canTrueString() {
    Role role = new Role("role");
    Permission permission_01 = new Permission("do something");
    role.addPermission(permission_01);
    assertTrue(role.can("do something"));
  }
}
