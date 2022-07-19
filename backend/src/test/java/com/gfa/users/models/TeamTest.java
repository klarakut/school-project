package com.gfa.users.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

  @Test
  void can_create_team() {
    Team team = new Team("Gregor Gregorovic");
    Team team2 = new Team("Gregor Gregorovic");
    assertEquals("Gregor Gregorovic", team.getName());
  }
  @Test
  void can_add_the_same_user_only_once(){
    Team team = new Team("team");
    assertTrue(team.addUser(new User("username", "email", "password")));
    assertFalse(team.addUser(new User("username", "email", "password")));
  }



}
