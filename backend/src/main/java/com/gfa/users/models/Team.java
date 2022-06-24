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

  /*

    @ManyToMany
    @JoinTable(
            name = "teamPermissionDb",
            joinColumns = @JoinColumn(name = "team_Id"),
            inverseJoinColumns = @JoinColumn(name = "permission_Id"))
    Set<Permission> permissions;




  @ManyToMany (mappedBy = )
  Set<User> users;                    Integrate after the merging other models together

  @ManyToMany (mappedBy = )
  Set<Role> roles;

   */

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

  /*                                  Classes are not conneted so Unit test will be write after merging
  public void addUser(User user) {
    users.add(user);
  }
  public void removeUser(User user) {
    users.remove(user);
  }

  public void addPermision(Permission permission){
    permissions.add(permission);
  }

  public void removePermission(Permission permission){
    permissions.remove(permission);
  }
  public void addRole(Role role){
    roles.add(role);
  }

  public void removeRole(Role role){
    roles.remove(role);
  }

   */

  public boolean is(Team t) {
    return (name.equals(t.name));
  }

  public boolean can(Permission permission) {
    return can(permission.getAbility);
  }

  public boolean can(String ability) {
    for (Permission permission : permissions) {
      if (ability.equals(permission.getAbility())) {
        return true;
      }
    }
    for (Role role : roles) {
      if (ability.equals(role.getAbility())) {
        return true;
      }
    }
    return false;
  }
}
