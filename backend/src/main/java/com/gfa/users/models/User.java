package com.gfa.users.models;

import com.gfa.users.dtos.CreateUserRequestDto;
import java.security.SecureRandom;
import java.time.LocalDateTime;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String username;

  @Column(unique = true)
  private String email;

  private String password;
  private LocalDateTime verifiedAt;

  @Column(unique = true)
  private String verificationToken;

  private LocalDateTime verificationTokenExpiresAt;

  @Column(unique = true)
  @Nullable
  private String forgottenPasswordToken;

  @Nullable private LocalDateTime forgottenPasswordTokenExpiresAt;

  private LocalDateTime createdAt;

  @ManyToMany
  @JoinTable(
      name = "user_permission",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id"))
  private Set<Permission> permissions;

  @ManyToMany
  @JoinTable(
      name = "user_role",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  @ManyToMany
  @JoinTable(
      name = "user_team",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "team_id"))
  private Set<Team> teams;

  public User() {
    permissions = new HashSet<>();
    roles = new HashSet<>();
    teams = new HashSet<>();
  }

  public User(@NotNull String username, @NotNull String email, @NotNull String password) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.createdAt = LocalDateTime.now();
  }

  public User(
      @NotNull Long id,
      @NotNull String username,
      @NotNull String email,
      @NotNull String password,
      @NotNull LocalDateTime verifiedAt,
      @NotNull String verificationToken,
      @NotNull LocalDateTime verificationTokenExpiresAt,
      @Nullable String forgottenPasswordToken,
      @Nullable LocalDateTime forgottenPasswordTokenExpiresAt,
      @NotNull LocalDateTime createdAt) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.verifiedAt = verifiedAt;
    this.verificationToken = verificationToken;
    this.verificationTokenExpiresAt = verificationTokenExpiresAt;
    this.forgottenPasswordToken = forgottenPasswordToken;
    this.forgottenPasswordTokenExpiresAt = forgottenPasswordTokenExpiresAt;
    this.createdAt = createdAt;
  }

  SecureRandom random = new SecureRandom();
  Integer randomSecureValue = random.nextInt();

  public User(CreateUserRequestDto dto, Long expirationTime) {
    this(dto.username, dto.email, dto.password);
    this.verifiedAt = null;
    this.verificationToken = String.valueOf(randomSecureValue);
    this.verificationTokenExpiresAt = LocalDateTime.now().plusSeconds(expirationTime);
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(@NotNull String email) {
    this.email = email;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(@NotNull String password) {
    this.password = password;
  }

  public LocalDateTime getVerifiedAt() {
    return verifiedAt;
  }

  public String getVerificationToken() {
    return verificationToken;
  }

  public void setVerificationToken(@NotNull String verificationToken) {
    this.verificationToken = verificationToken;
    this.verifiedAt = null;
  }

  public LocalDateTime getVerificationTokenExpiresAt() {
    return verificationTokenExpiresAt;
  }

  @Nullable
  public String getForgottenPasswordToken() {
    return forgottenPasswordToken;
  }

  public void setForgottenPasswordToken(@Nullable String forgottenPasswordToken) {
    this.forgottenPasswordToken = forgottenPasswordToken;
  }

  @Nullable
  public LocalDateTime getForgottenPasswordTokenExpiresAt() {
    return forgottenPasswordTokenExpiresAt;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public boolean addTeam(Team team) {
    return teams.add(team);
  }

  public boolean removeTeam(Team team) {
    return teams.remove(team);
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
      if (permission.can(ability)) {
        return true;
      }
    }
    for (Role role : roles) {
      if (role.can(ability)) {
        return true;
      }
    }
    for (Team team : teams) {
      if (team.can(ability)) {
        return true;
      }
    }
    return false;
  }
}
