package com.gfa.users.models;

import java.util.HashSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
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

  @ManyToMany(mappedBy = "roles")
  Set<Permission> permissions;

  @ManyToMany(mappedBy = "roles")
  Set<User> users;

  @ManyToMany(mappedBy = "roles")
  Set<Team> teams;

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
      if (ability.equals(permission.getAbility())) {
        return true;
      }
    }
    return false;
  }
}
