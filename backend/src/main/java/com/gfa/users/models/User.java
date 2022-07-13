package com.gfa.users.models;

import com.gfa.common.dtos.CreateUserRequestDto;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import javax.persistence.*;
import java.security.SecureRandom;
import java.util.*;


@Entity
public class User{

  @Id
  @Column(unique = true)
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

  private Boolean mfa;
  private String secret;

  @ManyToMany(mappedBy = "users")
  Set<Permission> permissions;

  @ManyToMany(mappedBy = "users")
  Set<Role> roles;

  @ManyToMany(mappedBy = "users")
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


  // constructor for use in Spring Security
  public User(User user) {
    this.id = user.id;
    this.username = user.username;
    this.email = user.email;
    this.password = user.password;
    this.verifiedAt = user.verifiedAt;
    this.verificationToken = user.verificationToken;
    this.verificationTokenExpiresAt = user.verificationTokenExpiresAt;
    this.forgottenPasswordToken = user.forgottenPasswordToken;
    this.forgottenPasswordTokenExpiresAt = user.forgottenPasswordTokenExpiresAt;
    this.createdAt = user.createdAt;
    this.mfa = user.mfa;
    this.secret = user.secret;
  }

  public User(CreateUserRequestDto dto, Long expirationTime, String secret){
    this.username = dto.username;
    this.email = dto.email;
    this.password = dto.password;
    this.verifiedAt = null;
    this.verificationToken = String.valueOf(randomSecureValue);
    this.verificationTokenExpiresAt = new Date(System.currentTimeMillis() + expirationTime);
    this.secret = secret;
    this.mfa = true;
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

  public Date getCreatedAt() {
    return createdAt;
  }

  public Boolean isMfa() {
    return mfa;
  }

  public String getSecret() {
    return secret;
  }
}
