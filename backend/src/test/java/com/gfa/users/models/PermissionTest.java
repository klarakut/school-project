package com.gfa.users.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
