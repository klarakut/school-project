package com.gfa.users.services;

import com.gfa.common.dtos.*;
import com.gfa.users.Exceptions.*;
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


import java.util.ArrayList;
import java.util.List;

@Service
/**
 * This class is a service that provides access to the database.
 */
public class DatabaseTeamService implements TeamService {
  public final TeamRepository teamRepository;
  public final UserRepository userRepository;
  public final PermissionRepository permissionRepository;
  public final RoleRepository roleRepository;

  @Autowired
  public DatabaseTeamService(
      TeamRepository teamRepository,
      UserRepository userRepository,
      PermissionRepository permissionRepository,
      RoleRepository roleRepository) {
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
      TeamResponseDto response = new TeamResponseDto(team);
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
    boolean team = teamRepository.findByName(teamCreateRequestDto.getName()).isPresent();
    if(team){
      throw new InvalidTeamExsistException();
    }
    try {
      Team teamCreate = new Team(teamCreateRequestDto.getName());
      //teamRepository.save(teamCreate);
      return new TeamResponseDto(teamRepository.save(teamCreate));
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
    Team teamFound = teamRepository.findById(id).orElseThrow(() -> new InvalidTeamNotFoundException());
    return new TeamResponseDto(teamFound);
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
    try {
      Team teamUpdate = teamRepository.findById(id).orElseThrow(() -> new InvalidTeamNotFoundException());
      teamUpdate.setName(teamPatchRequestDto.getName());
      //teamRepository.save(teamUpdate);
      return new TeamResponseDto(teamRepository.save(teamUpdate));
    } catch (InvalidTeamNotFoundException e) {
      throw new InvalidTeamNotFoundException();
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
  public EmptyResponseDto destroy(Long id) {

    // #Invalid Id input
    if (id <= 0) {
      throw new InvalidIdException();
    }
    try {
      Team teamDestroy = teamRepository.findById(id).orElseThrow( () -> new InvalidTeamNotFoundException());
      teamRepository.delete(teamDestroy);
      return new EmptyResponseDto("ok");
   } catch (InvalidTeamNotFoundException e) {
     throw new InvalidTeamNotFoundException();
    }catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
  public StatusResponseDto addUserToTeam(Long id, UserRequestDto userRequestDto) {
    // #Invalid Id input
    if (id <= 0 || userRequestDto.getId() <= 0) {
      throw new InvalidIdException();
    } try{
      Team teamFound = teamRepository.findById(id).orElseThrow( () -> new InvalidTeamNotFoundException());
      User userFound = userRepository.findById(userRequestDto.getId()).orElseThrow( () -> new InvalidUserNotFoundException());
      if (teamFound.getUsers().contains(userFound)){
        throw new InvalidUserExistsException();
      }

      if (teamFound.addUser(userFound)) {
        teamRepository.save(teamFound);
        return new StatusResponseDto("ok");
      } return null;
    } catch (InvalidTeamNotFoundException e) {
      throw new InvalidTeamNotFoundException();
    } catch (InvalidUserNotFoundException e) {
      throw new InvalidUserNotFoundException();
    }catch (InvalidUserExistsException e) {
      throw new InvalidUserExistsException();
    }catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
  public EmptyResponseDto deleteUserFromTeam(Long id, Long user_id) {
    // #Invalid Id input
    if (user_id <= 0 || id <= 0) {
      throw new InvalidIdException();
    }
    // #Team or User not found
    try {
      Team teamFound = teamRepository.findById(id).orElseThrow( () -> new InvalidTeamNotFoundException());
      User userFound = userRepository.findById(user_id).orElseThrow( () -> new InvalidUserNotFoundException());
      if (teamFound.removeUser(userFound)) {
        teamRepository.save(teamFound);
        return new EmptyResponseDto("ok");
      }
      return null;
    } catch (InvalidTeamNotFoundException e) {
      throw new InvalidTeamNotFoundException();
    } catch (InvalidUserNotFoundException e) {
      throw new InvalidUserNotFoundException();
    }catch (Exception e) {
    throw new UnknownErrorException();
  }
  }

  @Override
  public StatusResponseDto addPermissionsToTeam(
      Long id, PermissionRequestDto permissionRequestDto) {
    // #Invalid Id input
    if (id < 0 || permissionRequestDto.getId() < 0) {
      throw new InvalidIdException();
    }
    try {
      Team teamFound = teamRepository.findById(id).orElseThrow( () -> new InvalidTeamNotFoundException());
      Permission permission = permissionRepository.findById(id).orElseThrow( () -> new InvalidPermissionNotFoundException());
      if (teamFound.getPermissions().contains(permission)){
        throw new InvalidPermissionExistsException();
      }
      if(teamFound.addPermission(permission)){
        teamRepository.save(teamFound);
        return new StatusResponseDto("ok");
      }
      return null;
    } catch (InvalidTeamNotFoundException e) {
      throw new InvalidTeamNotFoundException();
    } catch (InvalidPermissionNotFoundException e) {
      throw new InvalidPermissionNotFoundException();
    }catch (InvalidPermissionExistsException e) {
      throw new InvalidPermissionExistsException();
    }catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
  public EmptyResponseDto deletePermissionFromTeam(Long id, Long permission_id) {
    // #Invalid Id input
    if (permission_id <= 0 || id <= 0) {
      throw new InvalidIdException();
    }
    try {
      Team teamFound = teamRepository.findById(id).orElseThrow( () -> new InvalidTeamNotFoundException());
      Permission permissionDelete = permissionRepository.findById(permission_id).orElseThrow( () -> new InvalidPermissionNotFoundException());
      if (teamFound.removePermission(permissionDelete)) {
        teamRepository.save(teamFound);
        return new EmptyResponseDto("ok");
      }
      return null;
    } catch (InvalidTeamNotFoundException e) {
      throw new InvalidTeamNotFoundException();
    } catch (InvalidPermissionNotFoundException e) {
      throw new InvalidPermissionNotFoundException();
    }catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
  public StatusResponseDto addRoleToTeam(Long id, RoleRequestDto roleRequestDto) {
    // #Invalid Id input
    if (id < 0) {
      throw new InvalidIdException();
    }
    try {
      Team teamFound = teamRepository.findById(id).orElseThrow( () -> new InvalidTeamNotFoundException());
      Role role = roleRepository.findById(roleRequestDto.getId()).orElseThrow( () -> new InvalidRoleNotFoundException());
      if (teamFound.getRoles().contains(role)){
        throw new InvalidRoleExistsException();
      }
      if (teamFound.addRole(role)) {
        teamRepository.save(teamFound);
        return new StatusResponseDto("ok");
      }
      return null;
    } catch (InvalidTeamNotFoundException e) {
      throw new InvalidTeamNotFoundException();
    } catch (InvalidRoleNotFoundException e) {
      throw new InvalidRoleNotFoundException();
    }catch (InvalidRoleExistsException e) {
      throw new InvalidRoleExistsException();
    }catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
  public EmptyResponseDto deleteRoleFromTeam(Long id, Long role_id) {
    // #Invalid Id input
    if (role_id <= 0 || id <= 0) {
      throw new InvalidIdException();
    }
    Team teamFound = teamRepository.findById(id).orElseThrow( () -> new InvalidTeamNotFoundException());
    Role roleDelete = roleRepository.findById(role_id).orElseThrow( () -> new InvalidRoleNotFoundException());
    try {
      if (teamFound.removeRole(roleDelete)) {
        teamRepository.save(teamFound);
        return new EmptyResponseDto("ok");
      }
    } catch (InvalidTeamNotFoundException e) {
      throw new InvalidTeamNotFoundException();
    } catch (InvalidRoleNotFoundException e) {
      throw new InvalidRoleNotFoundException();
    }catch (Exception e) {
      throw new UnknownErrorException();
    }
    return null;
  }
}
