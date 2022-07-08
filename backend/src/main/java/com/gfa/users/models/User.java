package com.gfa.users.models;

import com.gfa.common.dtos.CreateUserRequestDto;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import javax.persistence.*;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class User{

  @Id
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

  private boolean mfa;
  private String secret;

  @ManyToMany(mappedBy = "users")
  Set<Permission> permissions;

  @ManyToMany(mappedBy = "users")
  Set<Role> roles;

  @ManyToMany(mappedBy = "users")
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

  public boolean isMfa() {
    return mfa;
  }

  public String getSecret() {
    return secret;
  }

  public User(CreateUserRequestDto dto, Long expirationTime, String secret){
    //this(dto.username, dto.email, dto.password);
    this.username = dto.username;
    this.email = dto.email;
    this.password = dto.password;
    this.verifiedAt = null;
    this.verificationToken = String.valueOf(randomSecureValue);
    this.verificationTokenExpiresAt = new Date(System.currentTimeMillis() + expirationTime);
    this.secret = secret;
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
