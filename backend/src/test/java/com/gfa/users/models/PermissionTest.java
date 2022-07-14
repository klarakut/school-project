package com.gfa.users.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class PermissionTest {


  @Test
  void canCreatePermission() {
    Permission permission = new Permission("do something");
    assertEquals("do something", permission.getAbility());
  }

  @Test
  void canTrueForSamePermissions() {
    Permission p1 = new Permission("do something");
    Permission p2 = new Permission("do something");
    assertTrue(p1.can(p2));
  }

  @Test
  void canFalseForSamePermissions() {
    Permission p1 = new Permission("do something");
    Permission p2 = new Permission("do something else");
    assertFalse(p1.can(p2));
  }

  @Test
  void canFalseString() {
    Permission p1 = new Permission("do something");
    assertFalse(p1.can("something else"));
  }
}
