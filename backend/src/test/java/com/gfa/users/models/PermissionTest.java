package com.gfa.users.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


class PermissionTest {

  @Test
  void can_create_permission() {
    Permission permission = new Permission("do something");
    assertEquals("do something", permission.getAbility());
  }

  @Test
  void can_is_true_for_same_permissions() {
    Permission p1 = new Permission("do something");
    Permission p2 = new Permission("do something");
    assertTrue(p1.can(p2));
  }

  @Test
  void can_is_false_for_different_permissions() {
    Permission p1 = new Permission("do something");
    Permission p2 = new Permission("do something else");
    assertFalse(p1.can(p2));
  }

  @Test
  void can_is_false_for_different_abilities() {
    Permission p1 = new Permission("do something");
    assertFalse(p1.can("something else"));
  }
}
