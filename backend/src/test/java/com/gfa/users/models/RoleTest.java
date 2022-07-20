package com.gfa.users.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class RoleTest {
  Role role = new Role();

  @Test
  void create_role() {
    Role role = new Role("role");
    assertEquals("role", role.getRole());
  }

  @Test
  void can_is_false_for_add_same_permission_twice() {
    Role role = new Role("role");
    Permission permission = new Permission("do something");
    role.addPermission(permission);
    assertFalse(role.addPermission(permission));
  }

  @Test
  void can_remove_added_permission() {
    Role role = new Role("role");
    Permission permission = new Permission("do something");
    role.addPermission(permission);
    assertTrue(role.removePermission(permission));
    assertFalse(role.removePermission(permission));
    assertFalse(role.can(permission));
  }

  @Test
  void can_is_false_for_unadded_permission() {
    Role role = new Role("role");
    Permission permission = new Permission("do something");
    Permission otherPermission = new Permission("do something else");
    role.addPermission(permission);
    assertFalse(role.can(otherPermission));
  }

  @Test
  void can_is_true_for_added_permission() {
    role = new Role("role");
    Permission permission = new Permission("do something");
    role.addPermission(permission);
    assertTrue(role.can(permission));
  }

  @Test
  void can_is_false_for_unadded_permission_in_string() {
    Role role = new Role("role");
    Permission permission = new Permission("do something");
    role.addPermission(permission);
    assertFalse(role.can("do something else"));
  }

  @Test
  void can_is_true_for_added_permission_in_string() {
    Role role = new Role("role");
    Permission permission = new Permission("do something");
    role.addPermission(permission);
    assertTrue(role.can("do something"));
  }
}
