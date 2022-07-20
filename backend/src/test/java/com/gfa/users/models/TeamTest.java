package com.gfa.users.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class TeamTest {

  Permission permission;
  Role role;
  Team team;
  User user;

  @BeforeEach
  public void beforeEach() {
    permission = new Permission("permission");
    role = new Role("role");
    team = new Team("team");
    user = new User("user", "xx", "x");
  }

  @Test
  void get_name() {
    assertEquals("team", team.getName());
  }

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
