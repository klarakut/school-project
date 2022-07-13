package com.gfa.users.services;

import com.gfa.users.models.Team;
import com.gfa.users.repositories.PermissionRepository;
import com.gfa.users.repositories.RoleRepository;
import com.gfa.users.repositories.TeamRepository;
import com.gfa.users.repositories.UserRepository;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;

class DatabaseTeamServiceTest {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    DatabaseTeamServiceTest(TeamRepository teamRepository, UserRepository userRepository, PermissionRepository permissionRepository, RoleRepository roleRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
    }

    @Test
    void index() {
        Team team = new Team("Gregor");
        teamRepository.save(team);

        assertEquals()
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