package com.gfa.users.services;

import com.gfa.common.dtos.RolePatchRequestDto;
import com.gfa.common.dtos.RoleRequestDto;
import com.gfa.common.dtos.RoleResponseDto;
import com.gfa.common.dtos.StatusResponseDto;
import com.gfa.common.dtos.PermissionRequestDto;

import java.util.List;

public interface RoleService {
  RoleResponseDto store(RoleRequestDto dto);

  RoleResponseDto update(Long id, RolePatchRequestDto dto);

  RoleResponseDto show(Long id);

  List<RoleResponseDto> index();

  StatusResponseDto destroy(Long id);

  StatusResponseDto storePermission(Long id, PermissionRequestDto permission);

  StatusResponseDto destroyPermission(Long id, Long permissionId);
}
