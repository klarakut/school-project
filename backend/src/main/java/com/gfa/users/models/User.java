package com.gfa.users.models;

import com.gfa.common.dtos.CreateUserRequestDto;
import java.security.SecureRandom;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class User {// implements UserDetails {

    public static final long EXPIRATION_TIME = 60 * 60;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private Date verifiedAt;
    private String verificationToken;
    private Date verificationTokenExpiresAt;
    private Date createdAt;

    public User() {
    }

   public User(String username, String email, String password) {

        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(CreateUserRequestDto dto){
        this(dto.username, dto.email, dto.password);
        this.verifiedAt = null;
        this.verificationToken = String.valueOf(randomSecureValue);
        this.verificationTokenExpiresAt = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
    }

    SecureRandom random = new SecureRandom();
    Integer randomSecureValue = random.nextInt();

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Date getVerifiedAt() {
        return verifiedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public Date getVerificationTokenExpiresAt() {
        return verificationTokenExpiresAt;
    }

    /* @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        //true for now
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //return !locked; <- this should be field in User
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

       // return enabled; <- field in User
        return false;
    }
    */
}
