package com.gfa.users.services;

import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.common.dtos.StatusResponseDto;
import com.gfa.common.exceptions.InvalidTokenException;
import com.gfa.common.exceptions.TokenExpiredException;
import com.gfa.users.exceptions.EmailAlreadyVerifiedException;
import com.gfa.users.exceptions.EmailMissingException;
import com.gfa.users.exceptions.RequestBodyMissingException;
import com.gfa.users.exceptions.UserNotFoundException;
import com.gfa.users.models.User;
import com.gfa.users.repositories.UserRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseEmailVerificationService implements EmailVerificationService {

  private final UserRepository userRepository;

  @Autowired
  public DatabaseEmailVerificationService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public StatusResponseDto resend(EmailRequestDto dto) {
    if (dto == null) {
      throw new RequestBodyMissingException();
    }
    if (dto.email == null || dto.email.isEmpty()) {
      throw new EmailMissingException();
    }
    User user = userRepository.findByEmail(dto.email).orElseThrow(UserNotFoundException::new);
    user.setVerificationTokenExpiresAt(LocalDateTime.now().plusSeconds(3600));
    user.setVerificationToken("new token");
    userRepository.save(user);

    // send the verification email

    return new StatusResponseDto("ok");
  }

  public StatusResponseDto verify(String token) {

    if (token == null || token.isEmpty()) {
      throw new InvalidTokenException();
    }
    User user =
        userRepository.findByVerificationToken(token).orElseThrow(InvalidTokenException::new);

    if (user.getVerifiedAt() != null) {
      throw new EmailAlreadyVerifiedException();
    }

    if (LocalDateTime.now().isAfter(user.getVerificationTokenExpiresAt())) {
      throw new TokenExpiredException();
    }

    user.setVerified();
    return new StatusResponseDto("ok");
  }
}
