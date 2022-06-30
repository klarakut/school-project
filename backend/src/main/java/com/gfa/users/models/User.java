package com.gfa.users.models;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Entity
public class User {

  @id
  @Column(unique = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NotNull
  private Long id;

  @NotNull
  @Column(unique = true)
  private String username;

  @NotNull
  @Column(unique = true)
  private String email;

  @NotNull private String password;
  @NotNull private Date verifiedAt = null;

  @NotNull
  @Column(unique = true)
  private String verificationToken;

  @NotNull private Date verificationTokenExpiresAt;

  @Column(unique = true)
  @Nullable
  private String forgottenPasswordToken;

  @Nullable private Date forgottenPasswordTokenExpiresAt;

  @NotNull private Date createdAt;

  @ManyToMany(mappedBy = "user_permissions")
  Set<Permission> permissions;

  @ManyToMany(mappedBy = "user_roles")
  Set<Role> roles;

  @ManyToMany(mappedBy = "user_teams")
  Set<Team> teams;

  public User() {}

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

  public void setId(@NotNull Long id) {
    this.id = id;
  }

  @NotNull
  public String getEmail() {
    return email;
  }

  public void setEmail(@NotNull String email) {
    this.email = email;
  }

  @NotNull
  public Long getId() {
    return id;
  }

  @NotNull
  public String getUsername() {
    return username;
  }

  public void setUsername(@NotNull String username) {
    this.username = username;
  }

  @NotNull
  public String getPassword() {
    return password;
  }

  public void setPassword(@NotNull String password) {
    this.password = BCrypt.hashpw(password, BCrypt.gensalt(12));
  }

  @NotNull
  public Date getVerifiedAt() {
    return verifiedAt;
  }

  @NotNull
  public String getVerificationToken() {
    return verificationToken;
  }

  public void setVerificationToken(@NotNull String verificationToken) {
    this.verificationToken = verificationToken;
  }

  @NotNull
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

  public Date strToDate(String strDate) throws ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = simpleDateFormat.parse(strDate);
    return date;
  }

  /*public Boolean can(Permission permission){    This will be implemented in a future ticket.
    if can(permission.getAbility()) return true;
    for(Role role : roles){
      if(role.can(permissions))return true;}
    for (Team team : teams){
      if(team.is(permissions)) return true;
    }
    return false;
  }*/
}
