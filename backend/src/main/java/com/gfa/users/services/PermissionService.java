package com.gfa.users.services;

import com.gfa.users.dtos.*;
import com.gfa.users.models.Permission;
import com.gfa.users.repositories.PermissionRepository;

import java.util.List;

public interface PermissionService {

  List<PermissionResponseDto> index();

  PermissionResponseDto store(PermissionCreateRequestDto permissionCreateRequestDto);
  PermissionResponseDto show(Long id);
  PermissionResponseDto update(Long id, PermissionPatchRequestDto permissionPatchRequestDto);

  EmptyResponseDto destroy(Long id);
}
