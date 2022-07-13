package com.gfa.users.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails; 

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class UserDetailsEntity extends User implements UserDetails {

    // security User
   // private User user;

    /*public UserDetailsEntity(User user) {
        super();
        this.user = user;
    }*/
    public UserDetailsEntity(final User user){
        super(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
        /*return user.getAuthorities()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole))
                .collect(Collectors.toSet());
*/
       // return Collections.singleton(new SimpleGrantedAuthority(user.getRoles()));

    /*return getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toSet());*/
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    //extends User




    /*public UserDetailsEntity(final User user){
        super(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
        /*return getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());*/
  /*  }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    */
}
