package com.gfa.users.security;

import com.gfa.users.models.UserDetailsEntity;
import com.gfa.users.services.JwtTokenManager;
import com.gfa.users.services.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
  private final JwtConfiguration jwtConfiguration;
  private JwtTokenManager tokenProvider;
  private UserService userService;

  public JwtTokenAuthenticationFilter(JwtConfiguration jwtConfiguration, JwtTokenManager tokenProvider, UserService userService) {
    this.jwtConfiguration = jwtConfiguration;
    this.tokenProvider = tokenProvider;
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    String header = request.getHeader(jwtConfiguration.getHeader());

    if (header == null || !header.startsWith(jwtConfiguration.getPrefix())) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = header.replace(jwtConfiguration.getPrefix(), "");

    if (tokenProvider.validateToken(token)) {
      Claims claims = tokenProvider.getClaimsFromJwt(token);
      String username = claims.getSubject();

      UsernamePasswordAuthenticationToken auth =
                    userService.findByUsername(username)
                            .map(UserDetailsEntity::new)
                            .map(userDetails -> {
                              UsernamePasswordAuthenticationToken authenticationToken =
                                        new UsernamePasswordAuthenticationToken(
                                                userDetails, null, userDetails.getAuthorities());
                              authenticationToken
                                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                              return authenticationToken;
                            })
                            .orElse(null);

      SecurityContextHolder.getContext().setAuthentication(auth);
    } else {
      SecurityContextHolder.clearContext();
    }
    filterChain.doFilter(request, response);
  }
}
