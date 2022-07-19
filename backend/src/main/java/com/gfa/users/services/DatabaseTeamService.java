package com.gfa.users.services;

import com.gfa.users.dtos.TeamResponseDto;
import com.gfa.users.dtos.TeamCreateRequestDto;
import com.gfa.users.dtos.TeamPatchRequestDto;
import com.gfa.users.dtos.EmptyResponseDto;
import com.gfa.users.dtos.StatusResponseDto;
import com.gfa.users.dtos.UserRequestDto;
import com.gfa.users.dtos.PermissionRequestDto;
import com.gfa.users.dtos.RoleRequestDto;
import com.gfa.users.exceptions.InvalidRequestException;
import com.gfa.users.exceptions.InvalidTeamExsistException;
import com.gfa.users.exceptions.UnknownErrorException;
import com.gfa.users.exceptions.InvalidIdException;
import com.gfa.users.exceptions.InvalidTeamNotFoundException;
import com.gfa.users.exceptions.InvalidUserNotFoundException;
import com.gfa.users.exceptions.InvalidUserExistsException;
import com.gfa.users.exceptions.InvalidPermissionNotFoundException;
import com.gfa.users.exceptions.InvalidPermissionExistsException;
import com.gfa.users.exceptions.InvalidRoleNotFoundException;
import com.gfa.users.exceptions.InvalidRoleExistsException;
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

import java.util.List;
import java.util.stream.Collectors;

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
      throw new InvalidTeamExsistException();
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
    Team teamFound =
        teamRepository.findById(id).orElseThrow(() -> new InvalidTeamNotFoundException());
    return new TeamResponseDto(teamFound);
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
      Team teamUpdate =
          teamRepository.findById(id).orElseThrow(() -> new InvalidTeamNotFoundException());
      teamUpdate.setName(teamPatchRequestDto.getName());
      // teamRepository.save(teamUpdate);
      return new TeamResponseDto(teamRepository.save(teamUpdate));
    } catch (InvalidTeamNotFoundException e) {
      throw new InvalidTeamNotFoundException();
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
      Team teamDestroy =
          teamRepository.findById(id).orElseThrow(() -> new InvalidTeamNotFoundException());
      teamRepository.delete(teamDestroy);
      return new EmptyResponseDto("ok");
    } catch (InvalidTeamNotFoundException e) {
      throw new InvalidTeamNotFoundException();
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
      Team teamFound =
          teamRepository.findById(id).orElseThrow(() -> new InvalidTeamNotFoundException());
      User userFound =
          userRepository
              .findById(userRequestDto.getId())
              .orElseThrow(() -> new InvalidUserNotFoundException());

      if (teamFound.addUser(userFound)) {
        teamRepository.save(teamFound);
        return new StatusResponseDto("ok");
      }
      throw new InvalidUserExistsException();
    } catch (InvalidTeamNotFoundException e) {
      throw new InvalidTeamNotFoundException();
    } catch (InvalidUserNotFoundException e) {
      throw new InvalidUserNotFoundException();
    } catch (InvalidUserExistsException e) {
      throw new InvalidUserExistsException();
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
      Team teamFound =
          teamRepository.findById(id).orElseThrow(() -> new InvalidTeamNotFoundException());
      User userFound =
          userRepository.findById(userId).orElseThrow(() -> new InvalidUserNotFoundException());
      if (teamFound.removeUser(userFound)) {
        teamRepository.save(teamFound);
        return new EmptyResponseDto("ok");
      }
      throw new InvalidUserExistsException();
    } catch (InvalidTeamNotFoundException e) {
      throw new InvalidTeamNotFoundException();
    } catch (InvalidUserNotFoundException e) {
      throw new InvalidUserNotFoundException();
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
      Team teamFound =
          teamRepository.findById(id).orElseThrow(() -> new InvalidTeamNotFoundException());
      Permission permission =
          permissionRepository
              .findById(id)
              .orElseThrow(() -> new InvalidPermissionNotFoundException());
      if (teamFound.addPermission(permission)) {
        teamRepository.save(teamFound);
        return new StatusResponseDto("ok");
      }
      throw new InvalidPermissionExistsException();
    } catch (InvalidTeamNotFoundException e) {
      throw new InvalidTeamNotFoundException();
    } catch (InvalidPermissionNotFoundException e) {
      throw new InvalidPermissionNotFoundException();
    } catch (InvalidPermissionExistsException e) {
      throw new InvalidPermissionExistsException();
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
      Team teamFound =
          teamRepository.findById(id).orElseThrow(() -> new InvalidTeamNotFoundException());
      Permission permissionDelete =
          permissionRepository
              .findById(permissionId)
              .orElseThrow(() -> new InvalidPermissionNotFoundException());
      if (teamFound.removePermission(permissionDelete)) {
        teamRepository.save(teamFound);
        return new EmptyResponseDto("ok");
      }
      throw new InvalidPermissionExistsException();
    } catch (InvalidTeamNotFoundException e) {
      throw new InvalidTeamNotFoundException();
    } catch (InvalidPermissionNotFoundException e) {
      throw new InvalidPermissionNotFoundException();
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
      Team teamFound =
          teamRepository.findById(id).orElseThrow(() -> new InvalidTeamNotFoundException());
      Role role =
          roleRepository
              .findById(roleRequestDto.getId())
              .orElseThrow(() -> new InvalidRoleNotFoundException());
      if (teamFound.addRole(role)) {
        teamRepository.save(teamFound);
        return new StatusResponseDto("ok");
      }
      throw new InvalidRoleExistsException();
    } catch (InvalidTeamNotFoundException e) {
      throw new InvalidTeamNotFoundException();
    } catch (InvalidRoleNotFoundException e) {
      throw new InvalidRoleNotFoundException();
    } catch (InvalidRoleExistsException e) {
      throw new InvalidRoleExistsException();
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
  public EmptyResponseDto deleteRoleFromTeam(Long id, Long roleId) {
    if (roleId <= 0 || id <= 0) {
      throw new InvalidIdException();
    }
    Team teamFound =
        teamRepository.findById(id).orElseThrow(() -> new InvalidTeamNotFoundException());
    Role roleDelete =
        roleRepository.findById(roleId).orElseThrow(() -> new InvalidRoleNotFoundException());
    try {
      if (teamFound.removeRole(roleDelete)) {
        teamRepository.save(teamFound);
        return new EmptyResponseDto("ok");
      }
      throw new InvalidRoleExistsException();
    } catch (InvalidTeamNotFoundException e) {
      throw new InvalidTeamNotFoundException();
    } catch (InvalidRoleNotFoundException e) {
      throw new InvalidRoleNotFoundException();
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }
}
