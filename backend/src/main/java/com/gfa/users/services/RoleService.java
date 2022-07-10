package com.gfa.users.services;

import com.gfa.common.dtos.RoleCreateRequestDto;
import com.gfa.common.dtos.RolePatchRequestDto;
import com.gfa.common.dtos.RoleRequestDto;
import com.gfa.common.dtos.RoleResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleService {
    RoleResponseDto store(RoleCreateRequestDto dto);
    RoleResponseDto update(Long id, RolePatchRequestDto dto);
    RoleResponseDto show(Long id);
    List<RoleResponseDto> index();
    void destroy(Long id);
}
