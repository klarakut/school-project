package com.gfa.users.models;

import org.jetbrains.annotations.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

  public boolean can(Permission p) {
    return this.can(p.ability);
  }

  public boolean can(String ability) {
    return ability.equals(this.ability);
  }
}
