package com.gfa.users.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoleTest {
  Role role = new Role();

  @Test
  void can_create_role() {
    Role roleTest = new Role("role");
    Role roleSecondTest = new Role("role");
    assertEquals("role", roleTest.getRole());
    assertTrue(roleTest.is(roleSecondTest));
  }
}
