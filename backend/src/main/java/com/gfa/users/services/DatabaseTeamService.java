package com.gfa.users.services;

import com.gfa.common.dtos.*;
import com.gfa.users.models.Permission;
import com.gfa.users.models.Role;
import com.gfa.users.models.Team;
import com.gfa.users.models.User;
import com.gfa.users.repositories.PermissionRepository;
import com.gfa.users.repositories.RoleRepository;
import com.gfa.users.repositories.TeamRepository;
import com.gfa.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseTeamService implements TeamService {
  public final TeamRepository teamRepository;
  public final UserRepository userRepository;
  public final PermissionRepository permissionRepository;
  public final RoleRepository roleRepository;

  @Autowired
  public DatabaseTeamService(TeamRepository teamRepository, UserRepository userRepository, PermissionRepository permissionRepository, RoleRepository roleRepository) {
    this.teamRepository = teamRepository;
    this.userRepository = userRepository;
    this.permissionRepository = permissionRepository;
    this.roleRepository = roleRepository;
  }

  @Override
  public List<TeamResponseDto> index() {
    List<Team> findAllTeam = teamRepository.findAll();
    List<TeamResponseDto> findAllTeamResponse = new ArrayList<>();

    for (Team team : findAllTeam) {
      TeamResponseDto response = new TeamResponseDto(team.getId(), team.getName());
      findAllTeamResponse.add(response);
    }
    return findAllTeamResponse;
  }

  @Override
  public TeamResponseDto store(TeamCreateRequestDto teamCreateRequestDto) {
    // #Invalid input
    if (teamCreateRequestDto.getName().isEmpty()) {
      throw new InvalidRequestException();
    }
    // #Already exist
    boolean team = teamRepository.existByName(teamCreateRequestDto.getName());
    if (team) {
      throw new InvalidTeamExsistException();
    }
    // #Unauthorized User
    if (){
    }
    // #Insufficient rights
    if (){
    }
    // #Create a Team
      Team teamCreate = new Team(teamCreateRequestDto.getName());
      teamRepository.save(teamCreate);
      boolean teamWasCreated = teamRepository.existByName(teamCreate.getName());
      // #Server Error check
    if (!teamWasCreated){
      throw new ServerErrorException();
    }
  }

  @Override
  public TeamResponseDto show(Long id) {
    // #Invalid Id input
    if (id <= 0) {
      throw new InvalidIdException();
    }
    // #Team doesnt exist
    boolean team = teamRepository.existsById(id);
    if (!team) {
      throw new InvalidTeamNotFoundException();
    }
    Team teamFound = teamRepository.findById(id).get();
    return new TeamResponseDto(teamFound.getId(), teamFound.getName());
  }

  @Override
  public TeamResponseDto update(Long id, TeamPatchRequestDto teamPatchRequestDto) {
    // #Invalid Id input
    if (id <= 0) {
      throw new InvalidIdException();
    }
    // #Invalid  input
    if (teamPatchRequestDto.getName().isEmpty()) {
      throw new InvalidRequestException();
    }
    // #Team was not found
    boolean team = teamRepository.existsById(id);
    if (!team) {
      throw new InvalidTeamNotFoundException();
    }
    // #Unauthorized User
    if (){
    }
    // #Insufficient right
    if (){
    }
    // #Update a Team
    Team teamUpdate = teamRepository.findById(id).get();
    teamUpdate.setName(teamPatchRequestDto.getName());
    teamRepository.save(teamUpdate);
    return new TeamResponseDto(teamUpdate.getId(), teamUpdate.getName());

    // #Server error
    if (!teamUpdate.getName().equals(teamPatchRequestDto.getName())){
      throw new ServerErrorException();
    }
  }

  @Override
  public void destroy(Long id) {

    // #Invalid Id input
    if (id <= 0) {
      throw new InvalidIdException();
    }
    // #Team was not found
    boolean team = teamRepository.existsById(id);
    if (!team) {
      throw new InvalidTeamNotFoundException();
    }
    // #Unauthorized User
    if(){
    }
    // #Insufficient right
    if(){
    }
    // #Destroy a Team
    Team teamDestroy = teamRepository.findById(id).get();
    teamRepository.delete(teamDestroy);
    // #Server error
    if (team){
      throw new InvalidServerError();
    }
  }

  @Override
  public void addUserToTeam(Long id, UserRequestDto userRequestDto) {
    // #Invalid Id input
    if (id <= 0){
      throw new InvalidEmptyException();
    }
    // #Unauthorized User
    if(){
    }
    // #Insufficient right
    if(){
    }
    // #Team was not found
    boolean team = teamRepository.existsById(id);
    if (!team) {
      throw new InvalidTeamNotFoundException();
    }
    // #Add user to the team
    Team teamFound = teamRepository.findById(id).get();
    User user = userRepository.findById(userRequestDto.getId()).get();
    teamFound.addUser(user);
    teamRepository.save(teamFound);
    // #Server error
    if (){
    }
  }

  @Override
  public void deleteUserFromTeam(Long id, Long user_id) {
    // #Invalid Id input
    if (user_id <= 0 || id <=0) {
      throw new InvalidIdException();
    }
    // #Unauthorized User
    if(){
    }
    // #Insufficient right
    if(){
    }
    // #Team or User not found
    boolean team = teamRepository.existsById(id);
    boolean user = userRepository.existsById(user_id);
    if (!team || !user) {
      throw new InvalidTeamAndUserNotFoundException();
    }
    // #Delete user to the team
    Team teamFound = teamRepository.findById(id).get();
    User userFound = userRepository.findById(user_id).get();
    teamFound.removeUser(userFound);
    teamRepository.save(teamFound);
    // #Server error
    if (){
    }
  }
  @Override
  public void addPermissionsToTeam(Long id, PermissionRequestDto permissionRequestDto) {
    // #Invalid Id input
    if (id < 0){
      throw new InvalidEmptyException();
    }
    // #Unauthorized User
    if(){
    }
    // #Insufficient right
    if(){
    }
    // #Team was not found
    boolean team = teamRepository.existsById(id);
    if (!team) {
      throw new InvalidTeamNotFoundException();
    }
    // #Create permission to the team
    Team teamFound = teamRepository.findById(id).get();
    Permission permission = permissionRepository.findById(id).get();
    teamFound.addPermision(permission);
    teamRepository.save(teamFound);
    // #Server error
    if (!teamFound.can(permission)){
      throw new InvalidServerError();
    }
  }

  @Override
  public void deletePermissionFromTeam(Long id, Long permission_id) {
    // #Invalid Id input
    if (permission_id <= 0 || id <=0) {
      throw new InvalidIdException();
    }
    // #Unauthorized User
    if(){
    }
    // #Insufficient right
    if(){
    }
    // #Team or Permission not found
    boolean team = teamRepository.existsById(id);
    boolean permission = permissionRepository.existsById(permission_id);
    if (!team || !permission) {
      throw new InvalidTeamAndPermissionNotFoundException();
    }
    // #Delete a permission from Team
    Team teamFound = teamRepository.findById(id).get();
    Permission permissionDelete = permissionRepository.findById(permission_id).get();
    teamFound.removePermission(permissionDelete);
    teamRepository.save(teamFound);
    // #Server error
    if (teamFound.can(permissionDelete)){
      throw new InvalidServerError();
    }
  }

  @Override
  public void addRoleToTeam(Long id, RoleRequestDto roleRequestDto) {
    // #Invalid Id input
    if (id < 0){
      throw new InvalidEmptyException();
    }
    // #Unauthorized User
    if(){
    }
    // #Insufficient right
    if(){
    }
    // #Team was not found
    boolean team = teamRepository.existsById(id);
    if (!team) {
      throw new InvalidTeamNotFoundException();
    }
    // #Create Role from Team
    Team teamFound = teamRepository.findById(id).get();
    Role role = roleRepository.findById(roleRequestDto.getId()).get();
    teamFound.addRole(role);
    teamRepository.save(teamFound);
    // #Server error
    if (){
    }
  }

  @Override
  public void deleteRoleFromTeam(Long id, Long role_id) {
    // #Invalid Id input
    if (role_id <= 0 || id <=0) {
      throw new InvalidIdException();
    }
    // #Unauthorized User
    if(){
    }
    // #Insufficient right
    if(){
    }
    // #Team or Role not found
    boolean team = teamRepository.existsById(id);
    boolean role = roleRepository.existsById(role_id);
    if (!team || !role) {
      throw new InvalidTeamAndRoleNotFoundException();
    }
    // #Delete a Role from Team
    Team teamFound = teamRepository.findById(id).get();
    Role roleDelete = roleRepository.findById(role_id).get();
    teamFound.removeRole(roleDelete);
    teamRepository.save(teamFound);
    // #Server error
    if (){
    }
  }


}
