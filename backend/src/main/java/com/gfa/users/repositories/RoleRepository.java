package com.gfa.users.repositories;

import com.gfa.users.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Role findByRole(String role);

  // extends JpaRepository<Role, Long> {
  // Optional<Role> findByName(String name);
}
