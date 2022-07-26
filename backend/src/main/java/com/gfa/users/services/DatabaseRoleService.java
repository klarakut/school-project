package com.gfa.users.services;

import com.gfa.common.dtos.PermissionRequestDto;
import com.gfa.common.dtos.RoleRequestDto;
import com.gfa.common.dtos.RoleResponseDto;
import com.gfa.common.dtos.StatusResponseDto;
import com.gfa.users.exceptions.InvalidIdException;
import com.gfa.users.exceptions.InvalidInputException;
import com.gfa.users.exceptions.PermissionIdNotFoundException;
import com.gfa.users.exceptions.UnknownErrorException;
import com.gfa.common.exceptions.EmptyBodyException;
import com.gfa.users.exceptions.IdNotFoundException;
import com.gfa.users.exceptions.DuplicateRoleException;
import com.gfa.users.models.Permission;
import com.gfa.users.models.Role;
import com.gfa.users.repositories.PermissionRepository;
import com.gfa.users.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DatabaseRoleService implements RoleService {

  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;

  @Autowired
    public DatabaseRoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
    this.roleRepository = roleRepository;
    this.permissionRepository = permissionRepository;
  }

  @Override
    public List<RoleResponseDto> index() {
    List<Role> roles = roleRepository.findAll();
    return roles.stream().map(RoleResponseDto::new).collect(Collectors.toList());
  }

  @Override
    public RoleResponseDto show(Long id) {
    if (id <= 0) {
      throw new InvalidIdException();
    }

    Role role = roleRepository.findById(id).orElseThrow(() -> new IdNotFoundException());
    RoleResponseDto roleDto = new RoleResponseDto(role.getId(),role.getRole());
    return roleDto;
  }

  @Override
    public RoleResponseDto store(RoleRequestDto dto) {
    if (dto.role.isEmpty() || dto.role == null) {
      throw new EmptyBodyException();
    }

    boolean existingRole = roleRepository.findByRole(dto.role).isPresent();
    if (existingRole) {
      throw new DuplicateRoleException();
    }

    // TODO -> unauthorized user => 401
    // TODO -> insufficient rights to create roles => 403

    try {
      Role role = new Role(dto.role);
      roleRepository.save(role);
      RoleResponseDto roleDto = new RoleResponseDto(role.getId(),role.getRole());
      return roleDto;
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
    public RoleResponseDto update(Long id, RoleRequestDto dto) {
    if (id < 0) {
      throw new InvalidIdException();
    }

    if (dto.role.isEmpty() || dto.role == null) {
      throw new InvalidInputException();
    }

    // TODO -> unauthorized user => 401
    // TODO -> insufficient rights to create roles => 403

    Role role = roleRepository.findById(id).orElseThrow(() -> new IdNotFoundException());
    try {
      role.setRole(dto.role);
      roleRepository.save(role);
      RoleResponseDto roleDto = new RoleResponseDto(role.getId(), dto.role);
      return roleDto;
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
    public StatusResponseDto destroy(Long id) {
    if (id <= 0) {
      throw new InvalidIdException();
    }

    // TODO -> unauthorized user => 401
    // TODO -> insufficient rights to create roles => 403

    Role role = roleRepository.findById(id).orElseThrow(() -> new IdNotFoundException());
    roleRepository.deleteById(role.getId());
    StatusResponseDto status = new StatusResponseDto("ok");
    return status;
  }

  @Override
    public StatusResponseDto storePermission(Long id, PermissionRequestDto permissionRequestDto) {
    if (permissionRequestDto.ability.isEmpty()) {
      throw new InvalidInputException();
    }

    // TODO -> unauthorized user => 401
    // TODO -> insufficient rights to create roles => 403

    Permission permission = permissionRepository.findById(permissionRequestDto.id).orElseThrow(() -> new PermissionIdNotFoundException());
    Role role = roleRepository.findById(id).orElseThrow(() -> new IdNotFoundException());

    boolean hasPermission = role.can(permission);
    if (hasPermission) {
      throw new InvalidInputException();
    }

    try {
      role.addPermission(permission);
      roleRepository.save(role);
      StatusResponseDto status = new StatusResponseDto("ok");
      return status;
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
    public StatusResponseDto destroyPermission(Long id, Long permissionId) {

    // TODO -> unauthorized user => 401
    // TODO -> insufficient rights to create roles => 403

    Role role = roleRepository.findById(id).orElseThrow(() -> new IdNotFoundException());
    Permission permission = permissionRepository.findById(permissionId).orElseThrow(() -> new PermissionIdNotFoundException());

    boolean hasPermission = role.can(permission);
    if (!hasPermission) {
      throw new InvalidInputException();
    }

    try {
      role.removePermission(permission);
      roleRepository.save(role);
      StatusResponseDto status = new StatusResponseDto("ok");
      return status;
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }
}
