package com.gfa.users.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PermissionTest {

  Permission permission = new Permission();

  @Test
  void can_create_permission() {
    Permission permisionTest = new Permission("do something");
    Permission permisionSecondTest = new Permission("do something");
    assertEquals("do something", permisionTest.getAbility());
    assertTrue(permisionTest.can(permisionSecondTest));
  }
}
