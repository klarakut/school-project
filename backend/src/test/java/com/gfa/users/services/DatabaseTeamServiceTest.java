package com.gfa.users.services;

import com.gfa.common.dtos.*;
import com.gfa.users.Exceptions.InvalidIdException;
import com.gfa.users.Exceptions.InvalidPermissionNotFoundException;
import com.gfa.users.Exceptions.InvalidRequestException;
import com.gfa.users.Exceptions.InvalidTeamNotFoundException;
import com.gfa.users.models.Permission;
import com.gfa.users.models.Role;
import com.gfa.users.models.Team;
import com.gfa.users.models.User;
import com.gfa.users.repositories.PermissionRepository;
import com.gfa.users.repositories.RoleRepository;
import com.gfa.users.repositories.TeamRepository;
import com.gfa.users.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

class DatabaseTeamServiceTest {

  @Test
  void index() {
    // Arrange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);

    List<Team> teams = Arrays.asList(new Team("Gregor"));
    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, null, null);
    Mockito.when(mockedTeamRepository.findAll()).thenReturn(teams);
    // Act
    List<TeamResponseDto> result = teamService.index();
    // Assert
    assertEquals(1, result.size());
  }

  @Test
  void store() {
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    TeamCreateRequestDto teamDto = new TeamCreateRequestDto("Gregor");
    Team team = new Team(teamDto.getName());

    Mockito.when(mockedTeamRepository.save(Mockito.any())).thenReturn(team);

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, null, null);

    // Act
    TeamResponseDto result = teamService.store(teamDto);
    // Assert
    assertEquals("Gregor", result.team);
  }

  @Test
  void store_emptyRequest() {
    //AAA
    //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    TeamCreateRequestDto teamDto = new TeamCreateRequestDto("");
    Team team = new Team(teamDto.getName());
    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, null, null);

    //Act
    //Assert
    assertThrows(InvalidRequestException.class, () -> {teamService.store(teamDto);});

  }

  @Test
  void store_team_already_exists() {
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    TeamCreateRequestDto teamDto = new TeamCreateRequestDto("Gregor");
    Team team = new Team(teamDto.getName());

    Mockito.when(mockedTeamRepository.findByName(Mockito.any())).thenReturn(Optional.of(team));

    //Mockito.when(mockedTeamRepository.save(Mockito.any())).thenReturn(team);

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, null, null);

    // Act
    //TeamResponseDto result = teamService.store(teamDto);
    // Assert
    //assertEquals("Gregor", result.team);
    assertThrows(InvalidTeamNotFoundException.class, ()-> {teamService.store(teamDto);});
  }

  @Test
  void show() {
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);

    List<Team> teams = Arrays.asList(new Team("Gregor"), new Team("Gregorovic"));
    // Mockito.when(mockedTeamRepository.existsById(anyLong())).thenReturn(true);
    Mockito.when(mockedTeamRepository.findById(anyLong())).thenReturn(teams.stream().findFirst());

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, null, null);

    // Act
    TeamResponseDto result = teamService.show(1L);
    // Assert
    assertEquals("Gregor", result.team);
  }

  @Test
  void show_negativeId(){
    //AAA
    //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, null, null);
    //Assert
    assertThrows(InvalidIdException.class,() -> {teamService.show(-1L);});
  }
  @Test
  void update() {

    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    TeamPatchRequestDto teamDto = new TeamPatchRequestDto("Gregor");
    Team team1 = new Team("Gregooor");

    Mockito.when(mockedTeamRepository.findById(anyLong())).thenReturn(Optional.of(team1));
    Mockito.when(mockedTeamRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, null, null);
    // TeamResponseDto result = teamService.update(1L,teamDto);

    // Act
    TeamResponseDto result = teamService.update(1L, teamDto);
    // Assert
    assertEquals("Gregor", result.team);
  }

  @Test
  void update_emptyRequest(){
    //AAA
    //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    TeamPatchRequestDto teamDto = new TeamPatchRequestDto("");
    Team team = new Team(teamDto.getName());
    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, null, null);

    //Act
    //Assert
    assertThrows(InvalidRequestException.class, () -> {teamService.update(1L,teamDto);});

  }

  @Test
  void update_negativeId(){
    //AAA
    //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    TeamPatchRequestDto teamDto = new TeamPatchRequestDto("Greg");
    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, null, null);
    //Assert
    assertThrows(InvalidIdException.class,() -> {teamService.update(-1L,teamDto);});
  }

  @Test
  void destroy_negative_id() {

    //AAA
    //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    TeamPatchRequestDto teamDto = new TeamPatchRequestDto("Gregor");
    Team team1 = new Team("Gregooor");

    Mockito.when(mockedTeamRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(team1));

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, null, null);

    //Act
    //EmptyResponseDto result = teamService.destroy(-2L);
    //Assert
    assertThrows(InvalidIdException.class, () -> {teamService.destroy(-1L);});

    //assertEquals(InvalidIdException.class, result);
    /*

    /*
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    Team team1 = new Team("Gregor");
    List<Team> teams = new ArrayList<>(Arrays.asList(team1, new Team("Gregorovic")));

    Mockito.when(mockedTeamRepository.findById(anyLong())).thenReturn(Optional.of(team1));
    Mockito.doAnswer(
            i -> {teams.remove(0);return null;
            })
        .when(mockedTeamRepository)
        .delete(Mockito.any());
    Mockito.when(mockedTeamRepository.count()).thenReturn((long) teams.size()).thenReturn((long) teams.size());

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, null, null);
    assertEquals(2, mockedTeamRepository.count());

    // Act
    EmptyResponseDto result = teamService.destroy(1L);
    // Assert
    assertEquals(1, mockedTeamRepository.count());

     */
  }

  @Test
  void destroy_invalidId() {      //ToDo nefunguje !!!
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    //TeamPatchRequestDto teamDto = new TeamPatchRequestDto("Gregor");
    //Team team1 = new Team("Gregooor");
    //mockedTeamRepository.save(team1);

    Mockito.when(mockedTeamRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, null, null);

    //Act
    EmptyResponseDto result = teamService.destroy(5L);
    //Assert
    assertThrows(InvalidTeamNotFoundException.class, () -> {teamService.destroy(5L);});

  }

  @Test
  void destroy() {
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    //TeamPatchRequestDto teamDto = new TeamPatchRequestDto("Gregor");
    Team team1 = new Team("Gregooor");
    mockedTeamRepository.save(team1);

    Mockito.when(mockedTeamRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(team1));

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, null, null);

    //Act
    EmptyResponseDto result = teamService.destroy(1L);
    //Assert
    assertEquals("ok",result.status);
  }

  @Test
  void addUserToTeam() {

    //AAA
    //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    UserRepository mockedUserRepository = Mockito.mock(UserRepository.class);

    UserRequestDto userRequestDto = new UserRequestDto(1l, "Gregorovic");
    Team team = new Team("team");
    User user = new User();

    mockedTeamRepository.save(team);

    Mockito.when(mockedTeamRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(team));
    Mockito.when(mockedUserRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, mockedUserRepository, null, null);

    //Act
    StatusResponseDto result = teamService.addUserToTeam(1L,userRequestDto);
    //Assert
    assertEquals("ok", result.status);
  }

  @Test
  void addUserToTeam_negativeId() {
    //AAA
    //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    UserRepository mockedUserRepository = Mockito.mock(UserRepository.class);

    UserRequestDto userRequestDto = new UserRequestDto(1l, "Gregorovic");
    Team team = new Team("team");
    User user = new User();

    mockedTeamRepository.save(team);

    Mockito.when(mockedTeamRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(team));
    Mockito.when(mockedUserRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, mockedUserRepository, null, null);
    //Act
    //StatusResponseDto result = teamService.addUserToTeam(1L,userRequestDto);
    //Assert
    assertThrows(InvalidIdException.class,() -> {teamService.addUserToTeam(-1L,userRequestDto);});
  }

  @Test
  void addUserToTeam_cannotFindTeam() {   //ToDo nefunguje!!
    //AAA
    //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    UserRepository mockedUserRepository = Mockito.mock(UserRepository.class);

    UserRequestDto userRequestDto = new UserRequestDto(1l, "Gregorovic");
    Team team = new Team("team");
    User user = new User();

    mockedTeamRepository.save(team);

    Mockito.when(mockedTeamRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    Mockito.when(mockedUserRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, mockedUserRepository, null, null);
    //Act
   // StatusResponseDto result = teamService.addUserToTeam(1L,userRequestDto);
    //Assert
    assertThrows(InvalidTeamNotFoundException.class,() -> {teamService.addUserToTeam(1L,userRequestDto);});
  }

  @Test

  void addUserToTeam_cannotFindUser() {   //ToDo nefunguje!!
    //AAA
    //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    UserRepository mockedUserRepository = Mockito.mock(UserRepository.class);

    UserRequestDto userRequestDto = new UserRequestDto(1l, "Gregorovic");
    Team team = new Team("team");
    User user = new User();

    mockedTeamRepository.save(team);

    Mockito.when(mockedTeamRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(team));
    Mockito.when(mockedUserRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, mockedUserRepository, null, null);
    //Act
    //StatusResponseDto result = teamService.addUserToTeam(1L,userRequestDto);
    //Assert
    assertThrows(InvalidTeamNotFoundException.class,() -> {teamService.addUserToTeam(1L,userRequestDto);});
  }

  @Test
  void deleteUserFromTeam() {
    //AAA
    //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    UserRepository mockedUserRepository = Mockito.mock(UserRepository.class);

    UserRequestDto userRequestDto = new UserRequestDto(1l, "Gregorovic");
    Team team = new Team("team");
    User user = new User();

    Mockito.when(mockedTeamRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(team));
    Mockito.when(mockedUserRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, mockedUserRepository, null, null);

    //Act
    EmptyResponseDto result = teamService.deleteUserFromTeam(1L,1L);
    //Assert
    assertEquals("ok", result.status);
  }

  @Test
  void deleteUserFromTeam_negativeId(){
    //AAA
    //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    UserRepository mockedUserRepository = Mockito.mock(UserRepository.class);


    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, mockedUserRepository, null, null);
    //Act
    //Assert
    assertThrows(InvalidIdException.class,() -> {teamService.deleteUserFromTeam(-1L,-1L);});
  }

  @Test
  void deleteUserFromTeam_cannotFindTeam() {      //ToDo nefunguje!!
    //AAA
    //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    UserRepository mockedUserRepository = Mockito.mock(UserRepository.class);

    Team team = new Team("team");
    User user = new User();

    Mockito.when(mockedTeamRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    Mockito.when(mockedUserRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, mockedUserRepository, null, null);
    //Act
    //EmptyResponseDto result = teamService.deleteUserFromTeam(1L,1L);
    //Assert
    assertThrows(InvalidTeamNotFoundException.class,() -> {teamService.deleteUserFromTeam(1L,1L);});
  }
  @Test
  void deleteUserFromTeam_cannotFindUser() {      //ToDo nefunguje!!
    //AAA
    //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    UserRepository mockedUserRepository = Mockito.mock(UserRepository.class);

    Team team = new Team("team");
    User user = new User();

    Mockito.when(mockedTeamRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(team));
    Mockito.when(mockedUserRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, mockedUserRepository, null, null);
    //Act
    //EmptyResponseDto result = teamService.deleteUserFromTeam(1L,1L);
    //Assert
    assertThrows(InvalidTeamNotFoundException.class,() -> {teamService.deleteUserFromTeam(1L,1L);});
  }

  @Test
  void addPermissionsToTeam() {

    //AAA
    //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    PermissionRepository mockedPermissionRepository = Mockito.mock(PermissionRepository.class);

    PermissionRequestDto permissionRequestDto = new PermissionRequestDto(1l, null);
    Team team = new Team("team");
    Permission permission = new Permission("do something");



    Mockito.when(mockedTeamRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(team));
    Mockito.when(mockedPermissionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(permission));

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, mockedPermissionRepository, null);

    //Act
    StatusResponseDto result = teamService.addPermissionsToTeam(1L,permissionRequestDto);
    //Assert
    assertEquals("ok", result.status);
  }

  @Test
  void addPermissionsToTeam_negativeId(){
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    PermissionRepository mockedPermissionRepository = Mockito.mock(PermissionRepository.class);
    PermissionRequestDto permissionRequestDto = new PermissionRequestDto(-1l, null);

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, mockedPermissionRepository, null);

    //Act
    //StatusResponseDto result = teamService.addPermissionsToTeam(1L,permissionRequestDto);
    //Assert
    assertThrows(InvalidIdException.class, () -> {teamService.addPermissionsToTeam(1L,permissionRequestDto);});
  }
  @Test
  void addPermissionsToTeam_cannotFindTeam(){   //ToDo nefunguje!!

      //AAA
      //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    PermissionRepository mockedPermissionRepository = Mockito.mock(PermissionRepository.class);

    PermissionRequestDto permissionRequestDto = new PermissionRequestDto(1l, null);

    Team team = new Team("team");
      Permission permission = new Permission("do something");


    Mockito.when(mockedTeamRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    Mockito.when(mockedPermissionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(permission));

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, mockedPermissionRepository, null);

    //Act
    //StatusResponseDto result = teamService.addPermissionsToTeam(1L,permissionRequestDto);
    //Assert
    assertThrows(InvalidTeamNotFoundException.class, () -> {teamService.addPermissionsToTeam(1L,permissionRequestDto);});
  }

  @Test
  void addPermissionsToTeam_cannotFindPermissions(){    //ToDo nefunguje
    //AAA
    //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    PermissionRepository mockedPermissionRepository = Mockito.mock(PermissionRepository.class);

    PermissionRequestDto permissionRequestDto = new PermissionRequestDto(1l, null);

    Team team = new Team("team");
    Permission permission = new Permission("do something");


    Mockito.when(mockedTeamRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(team));
    Mockito.when(mockedPermissionRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, mockedPermissionRepository, null);

    //Act
    //StatusResponseDto result = teamService.addPermissionsToTeam(1L,permissionRequestDto);
    //Assert
    assertThrows(InvalidPermissionNotFoundException.class, () -> {teamService.addPermissionsToTeam(1L,permissionRequestDto);});
  }

  @Test
  void deletePermissionFromTeam() {
    //AAA
    //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    PermissionRepository mockedPermissionRepository = Mockito.mock(PermissionRepository.class);

    PermissionRequestDto permissionRequestDto = new PermissionRequestDto(1l, null);

    Team team = new Team("team");
    Permission permission = new Permission("do something");


    Mockito.when(mockedTeamRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(team));
    Mockito.when(mockedPermissionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(permission));

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, mockedPermissionRepository, null);

    //Act
    EmptyResponseDto result = teamService.deletePermissionFromTeam(1L,1L);
    //Assert
    assertEquals("ok", result.status);
  }

  @Test
  void deletePermissionFromTeam_negativeId(){
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    PermissionRepository mockedPermissionRepository = Mockito.mock(PermissionRepository.class);

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, mockedPermissionRepository, null);
    //Act
    //Assert
    assertThrows(InvalidIdException.class, () -> {teamService.deletePermissionFromTeam(1L,-1L);});
  }

  @Test
  void deletePermissionFromTeam_cannotFindTeam(){       //ToDo nefunguje!!

    //AAA
    //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    PermissionRepository mockedPermissionRepository = Mockito.mock(PermissionRepository.class);

    PermissionRequestDto permissionRequestDto = new PermissionRequestDto(1l, null);

    Team team = new Team("team");
    Permission permission = new Permission("do something");


    Mockito.when(mockedTeamRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    Mockito.when(mockedPermissionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(permission));

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, mockedPermissionRepository, null);

    //Act
    // Assert
    assertThrows(InvalidTeamNotFoundException.class, () -> {teamService.deletePermissionFromTeam(null,1L);});
  }

  @Test
  void deletePermissionFromTeam_cannotFindPermission(){   //ToDo nefunguje
    //AAA
    //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    PermissionRepository mockedPermissionRepository = Mockito.mock(PermissionRepository.class);

    PermissionRequestDto permissionRequestDto = new PermissionRequestDto(1l, null);

    Team team = new Team("team");
    Permission permission = new Permission("do something");


    Mockito.when(mockedTeamRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(team));
    Mockito.when(mockedPermissionRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, mockedPermissionRepository, null);

    //Act
    //StatusResponseDto result = teamService.addPermissionsToTeam(1L,permissionRequestDto);
    //Assert
    assertThrows(InvalidPermissionNotFoundException.class, () -> {teamService.deletePermissionFromTeam(1L,null);});
  }



  @Test
  void addRoleToTeam() {

    //AAA
    //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    RoleRepository mockedRoleRepository = Mockito.mock(RoleRepository.class);

    RoleRequestDto roleRequestDto = new RoleRequestDto(1l, null);
    Team team = new Team("team");
    Role role = new Role("something");



;
    Mockito.when(mockedTeamRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(team));
    Mockito.when(mockedRoleRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(role));

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, null, mockedRoleRepository);

    //Act
    StatusResponseDto result = teamService.addRoleToTeam(1L,roleRequestDto);
    //Assert
    assertEquals("ok", result.status);
  }

  @Test
  void addRoleToTeam_negativeId(){
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    RoleRepository mockedRoleRepository = Mockito.mock(RoleRepository.class);
    RoleRequestDto roleRequestDto = new RoleRequestDto(-1l, null);
    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, null, mockedRoleRepository);
    //Act
    //Assert
    assertThrows(InvalidIdException.class, () -> {teamService.addRoleToTeam(1L,roleRequestDto);});
  }

  @Test
  void addRoleToTeam_cannotFindTeam(){     //ToDo nefunguje!!

    //AAA
    //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    RoleRepository mockedRoleRepository = Mockito.mock(RoleRepository.class);

    RoleRequestDto roleRequestDto = new RoleRequestDto(1l, null);
    Team team = new Team("team");
    Role role = new Role("something");


    Mockito.when(mockedTeamRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    Mockito.when(mockedRoleRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(role));

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, null, mockedRoleRepository);

    //Act
    // Assert
    assertThrows(InvalidTeamNotFoundException.class, () -> {teamService.addRoleToTeam(1L,roleRequestDto);});
  }

  @Test
  void addRoleToTeam_cannotFindRole(){    //ToDo nefunguje!!

    //AAA
    //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    RoleRepository mockedRoleRepository = Mockito.mock(RoleRepository.class);

    RoleRequestDto roleRequestDto = new RoleRequestDto(1l, null);
    Team team = new Team("team");
    Role role = new Role("something");


    Mockito.when(mockedTeamRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(team));
    Mockito.when(mockedRoleRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, null, mockedRoleRepository);

    //Act
    // Assert
    assertThrows(InvalidTeamNotFoundException.class, () -> {teamService.addRoleToTeam(1L,roleRequestDto);});
  }


  @Test
  void deleteRoleFromTeam() {
    //AAA
    //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    RoleRepository mockedRoleRepository = Mockito.mock(RoleRepository.class);
    RoleRequestDto roleRequestDto = new RoleRequestDto(1l, null);
    Team team = new Team("team");
    Role role = new Role("something");


    Mockito.when(mockedTeamRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(team));
    Mockito.when(mockedRoleRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(role));

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, null, mockedRoleRepository);

    //Act
    EmptyResponseDto result = teamService.deleteRoleFromTeam(1L,1L);
    // Assert
    assertEquals("ok", result.status);
  }

  @Test
  void deleteRoleFromTeam_negativeId(){
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    RoleRepository mockedRoleRepository = Mockito.mock(RoleRepository.class);
    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, null, mockedRoleRepository);
    //Act
    //Assert
    assertThrows(InvalidIdException.class, () -> {teamService.deleteRoleFromTeam(1L,-1L);});
  }
  @Test
  void deleteRoleFromTeam_cannotFindTeam(){ //ToDo nefunguje!!

    //AAA
    //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    RoleRepository mockedRoleRepository = Mockito.mock(RoleRepository.class);

    RoleRequestDto roleRequestDto = new RoleRequestDto(1l, null);
    Team team = new Team("team");
    Role role = new Role("something");


    Mockito.when(mockedTeamRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    Mockito.when(mockedRoleRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(role));

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, null, mockedRoleRepository);

    //Act
    // Assert
    assertThrows(InvalidTeamNotFoundException.class, () -> {teamService.addRoleToTeam(1L,roleRequestDto);});
  }

  @Test
  void deleteRoleFromTeam_cannotFindRole(){//ToDo nefunguje!!

    //AAA
    //Arange
    TeamRepository mockedTeamRepository = Mockito.mock(TeamRepository.class);
    RoleRepository mockedRoleRepository = Mockito.mock(RoleRepository.class);

    RoleRequestDto roleRequestDto = new RoleRequestDto(1l, null);
    Team team = new Team("team");
    Role role = new Role("something");


    Mockito.when(mockedTeamRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(team));
    Mockito.when(mockedRoleRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

    TeamService teamService = new DatabaseTeamService(mockedTeamRepository, null, null, mockedRoleRepository);

    //Act
    // Assert
    assertThrows(InvalidTeamNotFoundException.class, () -> {teamService.addRoleToTeam(1L,roleRequestDto);});
  }
}
