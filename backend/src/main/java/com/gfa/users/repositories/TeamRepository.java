package com.gfa.users.repositories;


import com.gfa.users.models.Permission;
import com.gfa.users.models.Role;
import com.gfa.users.models.Team;
import com.gfa.users.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {

        Optional<Team> findById(Long id);
        boolean existByName(String name);

    Optional<Team> findByName(String name);
    boolean existByUsername(String username);
    Optional<Team> findByPermission(Permission permission);
    Optional<Team> findByRole(Role role);

}
