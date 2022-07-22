package com.gfa.users.controllers;

import com.gfa.users.repositories.PermissionRepository;
import com.gfa.users.repositories.RoleRepository;
import com.gfa.users.repositories.TeamRepository;
import com.gfa.users.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class TeamsRestControllerTest {

  @Autowired private MockMvc mvc;

  @Autowired private TeamRepository teamRepository;
  private RoleRepository roleRepository;
  private PermissionRepository permissionRepository;
  private UserRepository userRepository;

  @Test
  void get_index_shows_list_of_teams() {}

  @Test
  void get_index_shows_list_of_team() {}

  void store() {}

  @Test
  void show() {}

  @Test
  void update() {}

  @Test
  void destroy() {}

  @Test
  void addUserToTeam() {}

  @Test
  void deleteUserFromTeam() {}

  @Test
  void addPermissionsToTeam() {}

  @Test
  void deletePermissionFromTeam() {}

  @Test
  void addRoleToTeam() {}

  @Test
  void deleteRoleFromTeam() {}
}
