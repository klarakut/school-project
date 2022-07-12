package com.gfa.users.repositories;

import com.gfa.common.dtos.PermissionRequestDto;
import com.gfa.users.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
    Permission findByAbility(String ability);
}
