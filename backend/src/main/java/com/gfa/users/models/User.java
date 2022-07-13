package com.gfa.users.models;

import com.gfa.common.dtos.CreateUserRequestDto;
import java.security.SecureRandom;
import java.util.HashSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String username;

  @Column(unique = true)
  private String email;

  private String password;
  private Date verifiedAt;

  @Column(unique = true)
  private String verificationToken;

  private Date verificationTokenExpiresAt;

  @Column(unique = true)
  @Nullable
  private String forgottenPasswordToken;

  @Nullable private Date forgottenPasswordTokenExpiresAt;

  private Date createdAt;

  @ManyToMany
  @JoinTable(
          name = "user_permission",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "permission_id"))
  Set<Permission> permissions;

  @ManyToMany
  @JoinTable(
          name = "user_role",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  Set<Role> roles;

  @ManyToMany
  @JoinTable(
          name = "user_team",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "team_id"))
  Set<Team> teams;

  public User() {
    permissions = new HashSet<>();
    roles = new HashSet<>();
    teams = new HashSet<>();
  }

  public User(
      @NotNull String username,
      @NotNull String email,
      @NotNull String password,
      @NotNull Date createdAt) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.createdAt = createdAt;
  }

  public User(
      @NotNull Long id,
      @NotNull String username,
      @NotNull String email,
      @NotNull String password,
      @NotNull Date verifiedAt,
      @NotNull String verificationToken,
      @NotNull Date verificationTokenExpiresAt,
      @Nullable String forgottenPasswordToken,
      @Nullable Date forgottenPasswordTokenExpiresAt,
      @NotNull Date createdAt) {
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


  public User(CreateUserRequestDto dto, Long expirationTime){
    this(dto.username, dto.email, dto.password);
    this.verifiedAt = null;
    this.verificationToken = String.valueOf(randomSecureValue);
    this.verificationTokenExpiresAt = new Date(System.currentTimeMillis() + expirationTime);
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

  public Date getVerifiedAt() {
    return verifiedAt;
  }

  public String getVerificationToken() {
    return verificationToken;
  }

  public void setVerificationToken(@NotNull String verificationToken) {
    this.verificationToken = verificationToken;
  }

  public Date getVerificationTokenExpiresAt() {
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
  public Date getForgottenPasswordTokenExpiresAt() {
    return forgottenPasswordTokenExpiresAt;
  }


  public void setForgottenPasswordTokenExpiresAt(@Nullable Date forgottenPasswordTokenExpiresAt) {
    this.forgottenPasswordTokenExpiresAt = forgottenPasswordTokenExpiresAt;
  }

  @NotNull
  public Date getCreatedAt() {
    return createdAt;
  }

  public boolean can(Permission permission) {
    return can(permission.getAbility());
  }

  public boolean can(String ability) {

    if(this.getUsername().equals("root")) return true;  // root permissions

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
