package com.gfa.users.services;

import com.gfa.common.dtos.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleService {
    RoleResponseDto store(RoleCreateRequestDto dto);
    RoleResponseDto update(Long id, RolePatchRequestDto dto);
    RoleResponseDto show(Long id);
    List<RoleResponseDto> index();
    void destroy(Long id);

    void storePermission(Long id, PermissionRequestDto permission);

    void destroyPermission(Long id, Long permissionId);
}
