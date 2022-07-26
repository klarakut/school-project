package com.gfa.users.services;

import com.gfa.users.dtos.PermissionCreateRequestDto;
import com.gfa.users.dtos.PermissionResponseDto;
import com.gfa.users.dtos.PermissionPatchRequestDto;
import com.gfa.users.dtos.EmptyResponseDto;

import com.gfa.users.exceptions.InvalidRequestException;
import com.gfa.users.exceptions.PermissionExistsException;
import com.gfa.users.exceptions.UnknownErrorException;
import com.gfa.users.exceptions.InvalidIdException;
import com.gfa.users.exceptions.PermissionNotFoundException;

import com.gfa.users.models.Permission;
import com.gfa.users.repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DatabasePermissionService implements PermissionService {

  public final PermissionRepository permissionRepository;

  @Autowired
  public DatabasePermissionService(PermissionRepository permissionRepository) {
    this.permissionRepository = permissionRepository;
  }

  @Override
  public List<PermissionResponseDto> index() {
    List<Permission> findAllPermissions = permissionRepository.findAll();
    return findAllPermissions.stream().map(PermissionResponseDto::new).collect(Collectors.toList());
  }

  @Override
  public PermissionResponseDto store(PermissionCreateRequestDto permissionCreateRequestDto) {
    if (permissionCreateRequestDto.ability.isEmpty()) {
      throw new InvalidRequestException();
    }
    boolean permissions =
        permissionRepository.findByAbility(permissionCreateRequestDto.ability).isPresent();
    if (permissions) {
      throw new PermissionExistsException();
    }
    try {
      Permission permission = new Permission(permissionCreateRequestDto.ability);
      return new PermissionResponseDto(permissionRepository.save(permission));
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
  public PermissionResponseDto show(Long id) {
    if (id <= 0) {
      throw new InvalidIdException();
    }
    try {
      Permission permission =
          permissionRepository.findById(id).orElseThrow(() -> new PermissionNotFoundException());
      return new PermissionResponseDto(permission);
    } catch (PermissionNotFoundException e) {
      throw new PermissionNotFoundException();
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }

  @Override
  public PermissionResponseDto update(
      Long id, PermissionPatchRequestDto permissionPatchRequestDto) {
    if (id <= 0) {
      throw new InvalidIdException();
    }
    if (permissionPatchRequestDto.ability.isEmpty()) {
      throw new InvalidRequestException();
    }
    try {
      Permission permission =
          permissionRepository.findById(id).orElseThrow(() -> new PermissionNotFoundException());
      permission.setAbility(permissionPatchRequestDto.ability);
      return new PermissionResponseDto(permissionRepository.save(permission));
    } catch (PermissionNotFoundException e) {
      throw new PermissionNotFoundException();
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
      Permission permission =
          permissionRepository.findById(id).orElseThrow(() -> new PermissionNotFoundException());
      permissionRepository.delete(permission);
      return new EmptyResponseDto("ok");
    } catch (PermissionNotFoundException e) {
      throw new PermissionNotFoundException();
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
  }
}
