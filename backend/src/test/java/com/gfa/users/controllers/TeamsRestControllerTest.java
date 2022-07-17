package com.gfa.users.controllers;

import com.gfa.common.dtos.TeamResponseDto;
import com.gfa.users.models.Team;
import com.gfa.users.repositories.TeamRepository;
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
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@WebMvcTest(TeamsRestController.class)
class TeamsRestControllerTest {

  @Autowired private MockMvc mvc;

  private  TeamService teamService;
  private TeamRepository teamRepository;

/*
  @Test
  void index() throws Exception {
    List<TeamResponseDto> teams = Arrays.asList(new TeamResponseDto(1l,"Gregorovic"));
    Mockito.when(teamService.index()).thenReturn(teams);

    mvc.perform(MockMvcRequestBuilders.get("/teams"))
    .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", is(1l)))      /////??????
            .andExpect(jsonPath("$[0].team", is("Gregorovic")));
  }

  @Test
  void store() throws Exception {

    // create a post request to /api/foods
    // check the response is 200 (status ok)
    mvc.perform(MockMvcRequestBuilders.post("/teams")
                    .content("{\"name\":\"Gregorovic\"}")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", is(1l)))
            .andExpect(jsonPath("$[0].team", is("Gregorovic")));
  }

  @Test
  void show() throws Exception {
    Team team = new Team("Gregorovic");
    teamRepository.save(team);

    mvc.perform(MockMvcRequestBuilders.post("/teams/{id}")
            .content("{\"id\":\"1\"}")
            .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].team", is("Gregorovic")));
  }

  @Test
  void update() throws Exception {
    Team team = new Team("Gregorovic");
    teamRepository.save(team);

    mvc.perform(MockMvcRequestBuilders.patch("/teams/{id}")
                    .content("{\"name\":\"Janukovic\"}")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].team", is("Janukovic")));
  }

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

 */
}
