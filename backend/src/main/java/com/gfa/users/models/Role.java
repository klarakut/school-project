package com.gfa.users.models;

import org.jetbrains.annotations.NotNull;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
  private boolean deleted = Boolean.FALSE;

  @ManyToMany
  @JoinTable(
      name = "role_permission",
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id"))
  private Set<Permission> permissions;

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

  public void setRole(@NotNull String role) {
    this.role = role;
  }

  public boolean addPermission(Permission permission) {
    return permissions.add(permission);
  }

  public boolean removePermission(Permission permission) {
    return permissions.remove(permission);
  }

  public boolean can(Permission permission) {
    return can(permission.getAbility());
  }

  public boolean can(String ability) {
    for (Permission permission : permissions) {
      if (permission.can(ability)) {
        return true;
      }
    }
    return false;
  }
}
