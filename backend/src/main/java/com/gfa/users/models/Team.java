package com.gfa.users.models;

import java.util.HashSet;
import org.jetbrains.annotations.NotNull;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "teams")
public class Team {

  @Id
  @Column(unique = true)
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  @NotNull
  private String name;

  @ManyToMany
  @JoinTable(
      name = "team_role",
      joinColumns = @JoinColumn(name = "team_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  Set<Role> roles;

  @ManyToMany
  @JoinTable(
      name = "team_permission",
      joinColumns = @JoinColumn(name = "team_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id"))
  Set<Permission> permissions;

  @ManyToMany(mappedBy = "teams")
  Set<User> users;

  public Team() {
    permissions = new HashSet<>();
    roles = new HashSet<>();
  }

  public Team(@NotNull String name) {

    this();
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  @NotNull
  public Long getId() {
    return id;
  }

  public boolean addUser(User user) {
    return users.add(user);
  }

  public boolean removeUser(User user) {
    return users.remove(user);
  }

  public boolean addPermission(Permission permission) {
    return permissions.add(permission);
  }

  public boolean removePermission(Permission permission) {
    return permissions.remove(permission);
  }

  public boolean addRole(Role role) {
    return roles.add(role);
  }

  public boolean removeRole(Role role) {
    return roles.remove(role);
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
    for (Role role : roles) {
      if (role.can(ability)) {
        return true;
      }
    }
    return false;
  }
}
