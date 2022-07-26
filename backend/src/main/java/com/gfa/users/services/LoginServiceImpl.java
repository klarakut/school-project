package com.gfa.users.services;

import com.gfa.common.dtos.LoginRequestDto;
import com.gfa.common.dtos.StatusResponseDto;
import com.gfa.users.exceptions.EmailMissingException;
import com.gfa.users.exceptions.InvalidLoginCredentialsExpcetion;
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
public class LoginServiceImpl implements LoginService {

  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenManager jwtTokenManager;

  @Autowired
    public LoginServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtTokenManager jwtTokenManager) {
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
    String passwordToCheck = "fakePasswordToCheck";

    if (optionalUser.isPresent()) {
      user = optionalUser.get();
      passwordToCheck = user.getPassword();
      userCheckPassed = true;
    }

    if (!(user == null)) {
      try {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), dto.password));
        passwordCheckPassed = true;
      } catch (InvalidPasswordException ignored) {
        passwordCheckPassed = false;
      }
    }

    if (userCheckPassed && passwordCheckPassed) {
      //Authentication authentication = authenticationManager.authenticate(new )
      // Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.email,dto.password));
      /*String s = jwtTokenManager.generateToken(authentication);
            JwtResponseDto response = new JwtResponseDto(s);
            return response;*/
      return new StatusResponseDto("OK");
    }
    throw new InvalidLoginCredentialsExpcetion();
  }
}
