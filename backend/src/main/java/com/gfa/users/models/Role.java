package com.gfa.users.models;

import java.util.HashSet;
import javax.persistence.*;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NotNull
  private Long id;

  @NotNull
  @Column(unique = true, name = "role")
  private String role;

  @ManyToMany
  @JoinTable(
          name = "roles_permission",
          joinColumns = @JoinColumn(name = "role_id"),
          inverseJoinColumns = @JoinColumn(name = "permission_id"))
  Set<Permission> permissions;

  public Role() {
    permissions = new HashSet<>();
  }

  public Role(@NotNull String role) {
    this();
    this.role = role;
  }

  @NotNull
  public Long getId() {
    return id;
  }

  public String getRole() {
    return role;
  }

  public void addPermission(Permission p) {
    permissions.add(p);
  }

  public void removePermission(Permission p) {
    permissions.remove(p);
  }

  public boolean can(Permission permission) {
    return can(permission.getAbility());
  }

  public boolean can(String ability) {
    for (Permission permission : permissions) {
      if (permission.can(ability)) {}) {
        return true;
      }
    }
    return false;
  }
}
