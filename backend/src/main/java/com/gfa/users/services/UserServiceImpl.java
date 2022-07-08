package com.gfa.users.services;

import com.gfa.common.dtos.CreateUserRequestDto;
import com.gfa.common.dtos.ResponseDto;
import com.gfa.common.dtos.UserResponseDto;
import com.gfa.users.controllers.UserRestController;
import com.gfa.users.exceptions.*;
import com.gfa.users.models.User;
import com.gfa.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final EmailValidator emailValidator;
    private final UserRepository userRepository;
    private final Long tokenExpiration;
    private final JwtTokenManager jwtTokenManager;
    private final TotpManager totpManager;


    @Autowired
    public UserServiceImpl(EmailValidator emailValidator, UserRepository userRepository, JwtTokenManager jwtTokenManager, TotpManager totpManager) {
        this.emailValidator = emailValidator;
        this.userRepository = userRepository;
        //this.tokenExpiration = tokenExpiration; @Value("${config.security.token_expiration}") Long tokenExpiration,
        this.tokenExpiration = 60L;
        this.jwtTokenManager = jwtTokenManager;
        this.totpManager = totpManager;
    }

    //public ResponseEntity<? extends ResponseDto> store(CreateUserRequestDto dto) {
    public UserResponseDto store(CreateUserRequestDto dto) {

        if (dto.username.isEmpty() || dto.username == null) {
            throw new UsernameMissingException();
        }
        if (dto.password.isEmpty() || dto.password == null) {
            throw new PasswordMissingException();
        }
        if (dto.email.isEmpty() || dto.email == null) {
            throw new EmailMissingException();
        }

        boolean isValidEmail = emailValidator.isValid(dto.email);
        if (!isValidEmail) {
            throw new InvalidEmailException();
        }

        boolean usernameExist = userRepository.findByUsername(dto.username).isPresent();
        if (usernameExist) {
            throw new UsernameTakenException();
        }

        if (dto.username.length() < 4) {
            throw new ShortUsernameException();
        }

        if (dto.password.length() < 8) {
           throw new ShortPasswordException();
        }

        String secret = totpManager.generateSecret();
        User user = new User(dto, tokenExpiration, secret);
        userRepository.save(user);
        UserResponseDto userResponseDto = new UserResponseDto(user, totpManager.getUriForImage(user.getSecret()));

        boolean userCreated = userRepository.findByUsername(dto.username).isPresent();
        if (!userCreated) {
            throw new UnexpectedErrorException();
        }

        /*URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/register/{username}")
                .buildAndExpand(user.getUsername()).toUri();

        //return new ResponseEntity<>(userResponseDto,HttpStatus.CREATED);

        ResponseEntity
                .created(location)
                .body(userResponseDto);*/

        return userResponseDto;
    }

        @Override
        public Optional<User> findByUsername(String username) {
            return userRepository.findByUsername(username);
        }

        @Override
        public ResponseEntity<ResponseDto> index() {
            return null;
          }

        @Override
        public ResponseEntity<ResponseDto> show(Long id) {
        return null;
      }
}
