package com.gfa.users.controllers;

import com.gfa.common.dtos.TeamResponseDto;
import com.gfa.users.models.Team;
import com.gfa.users.repositories.PermissionRepository;
import com.gfa.users.repositories.RoleRepository;
import com.gfa.users.repositories.TeamRepository;
import com.gfa.users.repositories.UserRepository;
import com.gfa.users.services.TeamService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@WebMvcTest(TeamsRestController.class)
class TeamsRestControllerTest {

  @Autowired private MockMvc mvc;

  @Autowired
  private TeamRepository teamRepository;
  private RoleRepository roleRepository;
  private PermissionRepository permissionRepository;
  private UserRepository userRepository;

  @Test
  void get_index_shows_list_of_teams() throws Exception {
    //AAA
    //Arange
    assertEquals(0,teamRepository.count());
    teamRepository.save(new Team("Gregor"));
    teamRepository.save(new Team("Yeagor"));
    assertEquals(2,teamRepository.count());

    mvc.perform(MockMvcRequestBuilders.get("/teams")).andExpect(status().is2xxSuccessful());
  }

  @Test
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
