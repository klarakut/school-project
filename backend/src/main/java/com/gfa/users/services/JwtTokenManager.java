package com.gfa.users.services;

import com.gfa.users.security.JwtConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.MalformedJwtException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtTokenManager {
  private final JwtConfiguration jwtConfiguration;

  @Autowired
  public JwtTokenManager(JwtConfiguration jwtConfiguration) {
    this.jwtConfiguration = jwtConfiguration;
  }

  public String generateToken(Authentication authentication) {
    Long now = System.currentTimeMillis();
    return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("authorities",authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtConfiguration.getExpiration()))
                .signWith(SignatureAlgorithm.HS512,jwtConfiguration.getSecret().getBytes())
                .compact();
  }

  public Claims getClaimsFromJwt(String token) {
    return Jwts.parser()
                .setSigningKey(jwtConfiguration.getSecret().getBytes())
                .parseClaimsJws(token)
                .getBody();
  }

  public boolean validateToken(String authToken) {
    try {
      Jwts.parser()
                    .setSigningKey(jwtConfiguration.getSecret().getBytes())
                    .parseClaimsJws(authToken);
      return true;

    } catch (SignatureException ex) {
      throw new RuntimeException("Invalid JWT signature");
    } catch (MalformedJwtException ex) {
      throw new RuntimeException("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      throw new RuntimeException("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      throw new RuntimeException("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      throw new RuntimeException("JWT claims string is empty.");
    }
    //return false;
  }
}
