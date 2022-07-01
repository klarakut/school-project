package com.gfa.users.models;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Set;

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
      name = "team_permissions",
      joinColumns = @JoinColumn(name = "team_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id"))
  Set<Permission> permissions;

  @ManyToMany
  @JoinTable(
      name = "team_user",
      joinColumns = @JoinColumn(name = "team_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  Set<User> users;

  @ManyToMany(mappedBy = "teams")
  Set<Role> roles;

  public Team() {}

  public Team(@NotNull String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  @NotNull
  public Long getId() {
    return id;
  }

  public void addUser(User user) {
    users.add(user);
  }

  public void removeUser(User user) {
    users.remove(user);
  }

  public void addPermision(Permission permission) {
    permissions.add(permission);
  }

  public void removePermission(Permission permission) {
    permissions.remove(permission);
  }

  public void addRole(Role role) {
    roles.add(role);
  }

  public void removeRole(Role role) {
    roles.remove(role);
  }

  public boolean is(Team t) {
    return (name.equals(t.name));
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
