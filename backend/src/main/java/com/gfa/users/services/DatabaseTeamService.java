package com.gfa.users.services;

import com.gfa.users.dtos.EmptyResponseDto;
import com.gfa.users.dtos.PermissionRequestDto;
import com.gfa.users.dtos.RoleRequestDto;
import com.gfa.users.dtos.StatusResponseDto;
import com.gfa.users.dtos.TeamCreateRequestDto;
import com.gfa.users.dtos.TeamPatchRequestDto;
import com.gfa.users.dtos.TeamResponseDto;
import com.gfa.users.dtos.UserRequestDto;
import com.gfa.users.exceptions.InvalidIdException;
import com.gfa.users.exceptions.InvalidRequestException;
import com.gfa.users.exceptions.PermissionExistsException;
import com.gfa.users.exceptions.PermissionNotFoundException;
import com.gfa.users.exceptions.RoleExistsException;
import com.gfa.users.exceptions.RoleNotFoundException;
import com.gfa.users.exceptions.TeamExistsException;
import com.gfa.users.exceptions.TeamNotFoundException;
import com.gfa.users.exceptions.UnknownErrorException;
import com.gfa.users.exceptions.UserExistsException;
import com.gfa.users.exceptions.UserNotFoundException;
import com.gfa.users.models.Permission;
import com.gfa.users.models.Role;
import com.gfa.users.models.Team;
import com.gfa.users.models.User;
import com.gfa.users.repositories.PermissionRepository;
import com.gfa.users.repositories.RoleRepository;
import com.gfa.users.repositories.TeamRepository;
import com.gfa.users.repositories.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
    List<Team> findAllTeamResponse = teamRepository.findAll();
    return findAllTeamResponse.stream().map(TeamResponseDto::new).collect(Collectors.toList());
  }

  @Override
  public TeamResponseDto store(TeamCreateRequestDto teamCreateRequestDto) {
    if (teamCreateRequestDto.getName().isEmpty()) {
      throw new InvalidRequestException();
    }
    boolean team = teamRepository.findByName(teamCreateRequestDto.getName()).isPresent();
    if (team) {
      throw new TeamExistsException();
    }
    try {
      Team teamCreate = new Team(teamCreateRequestDto.getName());
      // teamRepository.save(teamCreate);
      return new TeamResponseDto(teamRepository.save(teamCreate));
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
  public TeamResponseDto show(Long id) {
    if (id <= 0) {
      throw new InvalidIdException();
    }
    try {
      Team teamFound = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException());
      return new TeamResponseDto(teamFound);
    } catch (TeamNotFoundException e) {
      throw new TeamNotFoundException();
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
  public TeamResponseDto update(Long id, TeamPatchRequestDto teamPatchRequestDto) {
    if (id <= 0) {
      throw new InvalidIdException();
    }
    if (teamPatchRequestDto.getName().isEmpty()) {
      throw new InvalidRequestException();
    }
    try {
      Team teamUpdate = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException());
      teamUpdate.setName(teamPatchRequestDto.getName());
      return new TeamResponseDto(teamRepository.save(teamUpdate));
    } catch (TeamNotFoundException e) {
      throw new TeamNotFoundException();
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
  public EmptyResponseDto destroy(Long id) {
    if (id <= 0) {
      throw new InvalidIdException();
    }
    try {
      Team teamDestroy = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException());
      teamRepository.delete(teamDestroy);
      return new EmptyResponseDto("ok");
    } catch (TeamNotFoundException e) {
      throw new TeamNotFoundException();
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
  public StatusResponseDto addUserToTeam(Long id, UserRequestDto userRequestDto) {
    if (id <= 0 || userRequestDto.getId() <= 0) {
      throw new InvalidIdException();
    }
    try {
      Team teamFound = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException());
      User userFound =
          userRepository
              .findById(userRequestDto.getId())
              .orElseThrow(() -> new UserNotFoundException());

      if (teamFound.addUser(userFound)) {
        teamRepository.save(teamFound);
        return new StatusResponseDto("ok");
      }
      throw new UserExistsException();
    } catch (TeamNotFoundException e) {
      throw new TeamNotFoundException();
    } catch (UserNotFoundException e) {
      throw new UserNotFoundException();
    } catch (UserExistsException e) {
      throw new UserExistsException();
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
  public EmptyResponseDto deleteUserFromTeam(Long id, Long userId) {
    if (userId <= 0 || id <= 0) {
      throw new InvalidIdException();
    }
    try {
      Team teamFound = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException());
      User userFound =
          userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
      if (teamFound.removeUser(userFound)) {
        teamRepository.save(teamFound);
        return new EmptyResponseDto("ok");
      }
      throw new UnknownErrorException();
    } catch (TeamNotFoundException e) {
      throw new TeamNotFoundException();
    } catch (UserNotFoundException e) {
      throw new UserNotFoundException();
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
  public StatusResponseDto addPermissionsToTeam(
      Long id, PermissionRequestDto permissionRequestDto) {
    if (id < 0 || permissionRequestDto.getId() < 0) {
      throw new InvalidIdException();
    }
    try {
      Team teamFound = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException());
      Permission permission =
          permissionRepository.findById(id).orElseThrow(() -> new PermissionNotFoundException());
      if (teamFound.addPermission(permission)) {
        teamRepository.save(teamFound);
        return new StatusResponseDto("ok");
      }
      throw new PermissionExistsException();
    } catch (TeamNotFoundException e) {
      throw new TeamNotFoundException();
    } catch (PermissionNotFoundException e) {
      throw new PermissionNotFoundException();
    } catch (PermissionExistsException e) {
      throw new PermissionExistsException();
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
  public EmptyResponseDto deletePermissionFromTeam(Long id, Long permissionId) {
    if (permissionId <= 0 || id <= 0) {
      throw new InvalidIdException();
    }
    try {
      Team teamFound = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException());
      Permission permissionDelete =
          permissionRepository
              .findById(permissionId)
              .orElseThrow(() -> new PermissionNotFoundException());
      if (teamFound.removePermission(permissionDelete)) {
        teamRepository.save(teamFound);
        return new EmptyResponseDto("ok");
      }
      throw new UnknownErrorException();
    } catch (TeamNotFoundException e) {
      throw new TeamNotFoundException();
    } catch (PermissionNotFoundException e) {
      throw new PermissionNotFoundException();
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
  public StatusResponseDto addRoleToTeam(Long id, RoleRequestDto roleRequestDto) {
    if (id < 0) {
      throw new InvalidIdException();
    }
    try {
      Team teamFound = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException());
      Role role =
          roleRepository
              .findById(roleRequestDto.getId())
              .orElseThrow(() -> new RoleNotFoundException());
      if (teamFound.addRole(role)) {
        teamRepository.save(teamFound);
        return new StatusResponseDto("ok");
      }
      throw new RoleExistsException();
    } catch (TeamNotFoundException e) {
      throw new TeamNotFoundException();
    } catch (RoleNotFoundException e) {
      throw new RoleNotFoundException();
    } catch (RoleExistsException e) {
      throw new RoleExistsException();
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
  public EmptyResponseDto deleteRoleFromTeam(Long id, Long roleId) {
    if (roleId <= 0 || id <= 0) {
      throw new InvalidIdException();
    }
    Team teamFound = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException());
    Role roleDelete =
        roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException());
    try {
      if (teamFound.removeRole(roleDelete)) {
        teamRepository.save(teamFound);
        return new EmptyResponseDto("ok");
      }
      throw new UnknownErrorException();
    } catch (TeamNotFoundException e) {
      throw new TeamNotFoundException();
    } catch (RoleNotFoundException e) {
      throw new RoleNotFoundException();
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }
}
