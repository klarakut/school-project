package com.gfa.users.services;

import com.gfa.users.dtos.PermissionCreateRequestDto;
import com.gfa.users.dtos.PermissionResponseDto;
import com.gfa.users.dtos.PermissionPatchRequestDto;
import com.gfa.users.dtos.EmptyResponseDto;

import java.util.List;

public interface PermissionService {

  List<PermissionResponseDto> index();

  PermissionResponseDto store(PermissionCreateRequestDto permissionCreateRequestDto);

  PermissionResponseDto show(Long id);

  PermissionResponseDto update(Long id, PermissionPatchRequestDto permissionPatchRequestDto);

  EmptyResponseDto destroy(Long id);
}
