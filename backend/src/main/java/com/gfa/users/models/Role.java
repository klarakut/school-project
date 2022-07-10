package com.gfa.users.models;

import java.util.HashSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@Entity
@Table(name = "roles")
@SQLDelete(sql = "UPDATE roles SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NotNull
  private Long id;

  @NotNull
  @Column(unique = true, name = "role")
  private String role;
  private Boolean deleted = Boolean.FALSE;

  @ManyToMany
  @JoinTable(
      name = "role_permission",
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id"))
  Set<Permission> permissions;

  @ManyToMany
  @JoinTable(
      name = "user_role",
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  Set<User> users;

  @ManyToMany
  @JoinTable(
      name = "team_role",
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "team_id"))
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

  public Boolean is(Role r) {
    return (role.equals(r.role));
  }

  public Boolean can(Permission permission) {
    return can(permission.getAbility());
  }

  public Boolean can(String ability) {
    for (Permission permission : permissions) {
      if (ability.equals(permission.getAbility())) {
        return true;
      }
    }
    return false;
  }
}
