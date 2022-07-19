package com.gfa.users.services;

import com.gfa.users.dtos.TeamResponseDto;
import com.gfa.users.dtos.TeamCreateRequestDto;
import com.gfa.users.dtos.TeamPatchRequestDto;
import com.gfa.users.dtos.EmptyResponseDto;
import com.gfa.users.dtos.StatusResponseDto;
import com.gfa.users.dtos.UserRequestDto;
import com.gfa.users.dtos.PermissionRequestDto;
import com.gfa.users.dtos.RoleRequestDto;

import java.util.List;

public interface TeamService {

  List<TeamResponseDto> index();

  TeamResponseDto store(TeamCreateRequestDto teamCreateRequestDto);

  TeamResponseDto show(Long id);

  TeamResponseDto update(Long id, TeamPatchRequestDto teamPatchRequestDto);

  EmptyResponseDto destroy(Long id);

  StatusResponseDto addUserToTeam(Long id, UserRequestDto userRequestDto); // //Zmeniť TeamResponse

  EmptyResponseDto deleteUserFromTeam(Long id, Long userId);

  StatusResponseDto addPermissionsToTeam(
      Long id, PermissionRequestDto permissionRequestDto); // //zmeniť Teamresponse

  EmptyResponseDto deletePermissionFromTeam(Long id, Long permissionIdd);

  StatusResponseDto addRoleToTeam(Long id, RoleRequestDto roleRequestDto);

  EmptyResponseDto deleteRoleFromTeam(Long id, Long roleId);
}
