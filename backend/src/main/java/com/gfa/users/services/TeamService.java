package com.gfa.users.services;

import com.gfa.common.dtos.*;

import java.util.List;

public interface TeamService {

     List<TeamResponseDto> index ();

     TeamResponseDto  store (TeamCreateRequestDto teamCreateRequestDto);

     TeamResponseDto show(Long id);

     TeamResponseDto update (Long id, TeamPatchRequestDto teamPatchRequestDto);

     void destroy(Long id);

     void addUserToTeam(Long id, UserRequestDto userRequestDto);             ////Zmeniť TeamResponse

    void deleteUserFromTeam(Long id, Long user_id);

    void addPermissionsToTeam(Long id, PermissionRequestDto permissionRequestDto); ////zmeniť Teamresponse

    void deletePermissionFromTeam(Long id, Long permission_id);

    void addRoleToTeam(Long id, RoleRequestDto roleRequestDto);

    void deleteRoleFromTeam(Long id, Long role_id);
}
