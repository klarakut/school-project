package com.gfa.users.models;

import java.util.HashSet;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
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
@SQLDelete(sql = "update soft_delete_jpa_example set deleted = 0 where id =?")
@Where(clause = "deleted = 1")
public class Team {

  @Id
  @Column(unique = true)
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  @NotNull
  private String name;

  private boolean deleted = Boolean.FALSE;

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

  public Team() {
    permissions = new HashSet<>();
    roles = new HashSet<>();
    users = new HashSet<>();
  }

  public Team(@NotNull String name) {

    this();
    this.name = name;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  public String getName() {
    return this.name;
  }

  public void setName(@NotNull String name) {
    this.name = name;
  }


  @NotNull
  public Long getId() {
    return id;
  }

  public boolean addUser(User user) {
    users.add(user);
    return true;
  }

  public boolean removeUser(User user) {
    users.remove(user);
    return true;
  }

  public boolean addPermision(Permission permission) {
    permissions.add(permission);
    return true;
  }

  public boolean removePermission(Permission permission) {
    permissions.remove(permission);
    return true;
  }

  public boolean addRole(Role role) {
    roles.add(role);
    return true;
  }

  public boolean removeRole(Role role) {
    roles.remove(role);
    return true;
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
