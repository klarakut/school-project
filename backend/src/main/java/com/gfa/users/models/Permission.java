package com.gfa.users.models;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "permissions")
public class Permission {
  @Id
  @Column(unique = true)
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  @NotNull
  private String ability;

  /*
  @ManyToMany (mappedBy = "permissions" )
  Set<Team> teams;


  @ManyToMany (mappedBy = )
  Set<User> users;                    Integrate after the merging other models together. I'll change it.

  @ManyToMany (mappedBy = )
  Set<Role> roles;

   */

  public Permission() {}

  public Permission(@NotNull String ability) {
    this.ability = ability;
  }

  @NotNull
  public Long getId() {
    return id;
  }

  @NotNull
  public String getAbility() {
    return ability;
  }

  public Boolean can(Permission p) {
    return (ability.equals(p.ability));
  }
}
