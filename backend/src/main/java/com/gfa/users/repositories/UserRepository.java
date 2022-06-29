package com.gfa.users.repositories;

import com.gfa.users.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findByEmail(String email);

  User findByForgottenPasswordToken(String resetToken);

  Boolean existsByForgottenPasswordToken(String resetToken);

  Boolean findByForgottenPasswordTokenExpiresAt (String resetToken);

  Optional<Object> findByUsernameOrEmail(String username, String email);

  Optional<Object> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
