package com.gfa.users.services;

import com.gfa.common.dtos.LoginRequestDto;
import com.gfa.common.dtos.StatusResponseDto;
import com.gfa.users.exceptions.EmailMissingException;
import com.gfa.users.exceptions.InvalidLoginCredentialsException;
import com.gfa.users.exceptions.InvalidPasswordException;
import com.gfa.users.exceptions.PasswordMissingException;
import com.gfa.users.exceptions.RequestBodyMissingException;
import com.gfa.users.models.User;
import com.gfa.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseLoginService implements LoginService {

  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenManager jwtTokenManager;

  @Autowired
    public DatabaseLoginService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtTokenManager jwtTokenManager) {
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
    this.jwtTokenManager = jwtTokenManager;
  }

  @Override
    public StatusResponseDto login(LoginRequestDto dto) {

    if (dto == null) {
      throw new RequestBodyMissingException();
    }
    if (dto.email.isEmpty() || dto.email == null) {
      throw new EmailMissingException();
    }
    if (dto.password.isEmpty() || dto.email == null) {
      throw new PasswordMissingException();
    }

    boolean userCheckPassed = false;
    boolean passwordCheckPassed = false;
    Optional<User> optionalUser = userRepository.findByEmail(dto.email);

    User user = null;
    String usernameToCheck = "";

    if (optionalUser.isPresent()) {
      user = optionalUser.get();
      usernameToCheck = user.getUsername();
      userCheckPassed = true;
    }

    try {
      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usernameToCheck, dto.password));
      passwordCheckPassed = true;
    } catch (InvalidPasswordException ignored) {
      passwordCheckPassed = false;
    }

    if (userCheckPassed && passwordCheckPassed) {
      return new StatusResponseDto("OK");
    }
    throw new InvalidLoginCredentialsException();
  }
}
