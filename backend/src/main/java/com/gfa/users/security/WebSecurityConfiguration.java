package com.gfa.users.security;

import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSecurityConfiguration {

  /*private UserServiceImpl userService;

  @Bean
  public BCryptPasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
      http
              .csrf().disable()
              .authorizeRequests()
              .antMatchers("/register/**")
              .permitAll()
              .anyRequest()
              .authenticated().and()
              .formLogin();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.authenticationProvider(daoAuthenticationProvider());
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider(){
      DaoAuthenticationProvider provider =
              new DaoAuthenticationProvider();
      provider.setPasswordEncoder(passwordEncoder());
      provider.setUserDetailsService(userService);
      return provider;
  }*/
}
