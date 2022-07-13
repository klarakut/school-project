package com.gfa.users.services;

import com.gfa.common.dtos.TeamResponseDto;
import com.gfa.users.models.Team;
import com.gfa.users.repositories.PermissionRepository;
import com.gfa.users.repositories.RoleRepository;
import com.gfa.users.repositories.TeamRepository;
import com.gfa.users.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(DatabaseTeamService.class)
class DatabaseTeamServiceTest {

    @Autowired
    private MockMvc mvc;

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final DatabaseTeamService databaseTeamService;

    DatabaseTeamServiceTest(TeamRepository teamRepository, UserRepository userRepository, PermissionRepository permissionRepository, RoleRepository roleRepository, DatabaseTeamService databaseTeamService) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.databaseTeamService = databaseTeamService;
    }

    @Test
    void index() {

    }

    @Test
    void store() {
    }

    @Test
    void show() {
    }

    @Test
    void update() {
    }

    @Test
    void destroy() {
    }

    @Test
    void addUserToTeam() {
    }

    @Test
    void deleteUserFromTeam() {
    }

    @Test
    void addPermissionsToTeam() {
    }

    @Test
    void deletePermissionFromTeam() {
    }

    @Test
    void addRoleToTeam() {
    }

    @Test
    void deleteRoleFromTeam() {
    }
}