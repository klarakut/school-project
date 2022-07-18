package com.gfa.users.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TeamTest {

  @Test
  void can_create_team() {
    Team team = new Team("Gregor Gregorovic");
    Team team2 = new Team("Gregor Gregorovic");
    assertEquals("Gregor Gregorovic", team.getName());
  }
}
