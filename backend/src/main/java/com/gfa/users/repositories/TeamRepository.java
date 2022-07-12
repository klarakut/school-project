package com.gfa.users.repositories;


import com.gfa.users.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {

        Optional<Team> findById(Long id);
        boolean existByName(String name);

}
