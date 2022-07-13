package com.gfa.users.services;

import com.gfa.common.dtos.*;

import java.util.List;

public interface TeamService {

     List<TeamResponseDto> index ();

     TeamResponseDto  store (TeamCreateRequestDto teamCreateRequestDto);

     TeamResponseDto show(Long id);

     TeamResponseDto update (Long id, TeamPatchRequestDto teamPatchRequestDto);

    EmptyResponseDto destroy(Long id);

    StatusResponseDto addUserToTeam(Long id, UserRequestDto userRequestDto);             ////Zmeniť TeamResponse

    EmptyResponseDto deleteUserFromTeam(Long id, Long user_id);

    StatusResponseDto addPermissionsToTeam(Long id, PermissionRequestDto permissionRequestDto); ////zmeniť Teamresponse

    EmptyResponseDto deletePermissionFromTeam(Long id, Long permission_id);

    StatusResponseDto addRoleToTeam(Long id, RoleRequestDto roleRequestDto);

    EmptyResponseDto deleteRoleFromTeam(Long id, Long role_id);
}
