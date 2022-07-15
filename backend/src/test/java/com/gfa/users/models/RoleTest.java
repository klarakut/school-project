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
  void can_is_false_for_add_same_permission_twice() {
    Role r1 = new Role("role");
    Permission permission_01 = new Permission("do something");
    r1.addPermission(permission_01);
    assertFalse(r1.addPermission(permission_01));
  }

  @Test
  void can_remove_added_permission() {
    Role r1 = new Role("role");
    Permission permission_01 = new Permission("do something");
    r1.addPermission(permission_01);
    assertTrue(r1.removePermission(permission_01));
    assertFalse(r1.removePermission(permission_01));
    assertFalse(r1.can(permission_01));
  }

  @Test
  void can_is_false_for_unadded_permission() {
    Role role = new Role("role");
    Permission permission_01 = new Permission("do something");
    Permission permission_02 = new Permission("do something else");
    role.addPermission(permission_01);
    assertFalse(role.can(permission_02));
  }

  @Test
  void can_is_true_for_added_permission() {
    role = new Role("role");
    Permission permission_01 = new Permission("do something");
    role.addPermission(permission_01);
    assertTrue(role.can(permission_01));
  }

  @Test
  void can_is_false_for_unadded_permission_in_string() {
    Role role = new Role("role");
    Permission permission_01 = new Permission("do something");
    role.addPermission(permission_01);
    assertFalse(role.can("do something else"));
  }

  @Test
  void can_is_true_for_added_permission_in_string() {
    Role role = new Role("role");
    Permission permission_01 = new Permission("do something");
    role.addPermission(permission_01);
    assertTrue(role.can("do something"));
  }
}
