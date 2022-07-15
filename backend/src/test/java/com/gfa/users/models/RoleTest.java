package com.gfa.users.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RoleTest {
  Role role = new Role();

  @Test
  void can_create_role() {
    Role roleTest = new Role("role");
    Role roleSecondTest = new Role("role");
    assertEquals("role", roleTest.getRole());
  }
}
