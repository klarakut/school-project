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
    // if (){
    // }
    // #Insufficient rights
    // if (){
    // }
    // #Create a Team
    try {
      Team teamCreate = new Team(teamCreateRequestDto.getName());
      teamRepository.save(teamCreate);
      return new TeamResponseDto(teamCreate.getId(),teamCreate.getName());
    } catch (Exception e) {
      throw new UnknownErrorException();
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
    //if (){
    //}
    // #Insufficient right
    //if (){
    //}
    try{
      Team teamUpdate = teamRepository.findById(id).get();
      teamUpdate.setName(teamPatchRequestDto.getName());
      teamRepository.save(teamUpdate);
      return new TeamResponseDto(teamUpdate.getId(), teamUpdate.getName());
    } catch (Exception e){
      throw new UnknownErrorException();
    }

  }

  @Override
  public EmptyResponseDto destroy(Long id) {

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
    //if(){
    //}
    // #Insufficient right
    //if(){
    //}
    // #Destroy a Team
    try{
      Team teamDestroy = teamRepository.findById(id).get();
      teamRepository.delete(teamDestroy);
      return new EmptyResponseDto();
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
  public StatusResponseDto addUserToTeam(Long id, UserRequestDto userRequestDto) {
    // #Invalid Id input
    if (id <= 0) {
      throw new InvalidEmptyException();
    }
    // #Unauthorized User
    // if(){
    // }
    // #Insufficient right
    // if(){
    // }
    // #Team was not found
    boolean team = teamRepository.existsById(id);
    if (!team) {
      throw new InvalidTeamNotFoundException();
    }
    try {
      Team teamFound = teamRepository.findById(id).get();
      User user = userRepository.findById(userRequestDto.getId()).get();
      if (teamFound.addUser(user)) {
        teamRepository.save(teamFound);
        return new StatusResponseDto("ok");
      }
      return null;

    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
  public EmptyResponseDto deleteUserFromTeam(Long id, Long user_id) {
    // #Invalid Id input
    if (user_id <= 0 || id <=0) {
      throw new InvalidIdException();
    }
    // #Unauthorized User
    //if(){
    //}
    // #Insufficient right
    //if(){
    //}
    // #Team or User not found
    boolean team = teamRepository.existsById(id);
    boolean user = userRepository.existsById(user_id);
    if (!team || !user) {
      throw new InvalidTeamAndUserNotFoundException();
    }
    try{
      Team teamFound = teamRepository.findById(id).get();
      User userFound = userRepository.findById(user_id).get();
      if (teamFound.removeUser(userFound)){
        teamRepository.save(teamFound);
        return new EmptyResponseDto();
      }
      return null;
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }
  @Override
  public StatusResponseDto addPermissionsToTeam(Long id, PermissionRequestDto permissionRequestDto) {
    // #Invalid Id input
    if (id < 0){
      throw new InvalidEmptyException();
    }
    // #Unauthorized User
    //if(){
    //}
    // #Insufficient right
    //if(){
    //}
    // #Team was not found
    boolean team = teamRepository.existsById(id);
    if (!team) {
      throw new InvalidTeamNotFoundException();
    }
    try{
      Team teamFound = teamRepository.findById(id).get();
      Permission permission = permissionRepository.findById(id).get();
      if (teamFound.addPermision(permission)){
        teamRepository.save(teamFound);
        return new StatusResponseDto("ok");
      }
      return null;
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
  public EmptyResponseDto deletePermissionFromTeam(Long id, Long permission_id) {
    // #Invalid Id input
    if (permission_id <= 0 || id <=0) {
      throw new InvalidIdException();
    }
    // #Unauthorized User
    //if(){
    //}
    // #Insufficient right
    //if(){
    //}
    // #Team or Permission not found
    boolean team = teamRepository.existsById(id);
    boolean permission = permissionRepository.existsById(permission_id);
    if (!team || !permission) {
      throw new InvalidTeamAndPermissionNotFoundException();
    }
    try{
      Team teamFound = teamRepository.findById(id).get();
      Permission permissionDelete = permissionRepository.findById(permission_id).get();
      if (teamFound.removePermission(permissionDelete)){
        teamRepository.save(teamFound);
        return new EmptyResponseDto();
      }
      return null;
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
  public StatusResponseDto addRoleToTeam(Long id, RoleRequestDto roleRequestDto) {
    // #Invalid Id input
    if (id < 0){
      throw new InvalidEmptyException();
    }
    // #Unauthorized User
    //if(){
    //}
    // #Insufficient right
    //if(){
    //}
    // #Team was not found
    boolean team = teamRepository.existsById(id);
    if (!team) {
      throw new InvalidTeamNotFoundException();
    }
    try{
      Team teamFound = teamRepository.findById(id).get();
      Role role = roleRepository.findById(roleRequestDto.getId()).get();
     if(teamFound.addRole(role)){
       teamRepository.save(teamFound);
       return new StatusResponseDto("ok");
     }
     return null;
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
  public EmptyResponseDto deleteRoleFromTeam(Long id, Long role_id) {
    // #Invalid Id input
    if (role_id <= 0 || id <=0) {
      throw new InvalidIdException();
    }
    // #Unauthorized User
    //if(){
    //}
    // #Insufficient right
    //if(){
    //}
    // #Team or Role not found
    boolean team = teamRepository.existsById(id);
    boolean role = roleRepository.existsById(role_id);
    if (!team || !role) {
      throw new InvalidTeamAndRoleNotFoundException();
    }
    try{
      Team teamFound = teamRepository.findById(id).get();
      Role roleDelete = roleRepository.findById(role_id).get();
      if (teamFound.removeRole(roleDelete)){
        teamRepository.save(teamFound);
        return new EmptyResponseDto();
      }
      return null;
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }


}
