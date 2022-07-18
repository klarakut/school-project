package com.gfa.users.models;

import java.util.HashSet;
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

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.jetbrains.annotations.NotNull;

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

  public boolean addPermission(Permission p) {
    return permissions.add(p);
  }

  public boolean removePermission(Permission p) {
    return permissions.remove(p);
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
