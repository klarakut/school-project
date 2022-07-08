package com.gfa.users.services;

import com.gfa.common.dtos.CreateUserRequestDto;
import com.gfa.common.dtos.ErrorResponseDto;
import com.gfa.common.dtos.ResponseDto;
import com.gfa.common.dtos.UserResponseDto;
import com.gfa.users.models.User;
import com.gfa.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final EmailValidator emailValidator;
    private final UserRepository userRepository;
    private final Long tokenExpiration;
    private final JwtTokenManager jwtTokenManager;
    private final TotpManager totpManager;

    public UserServiceImpl(EmailValidator emailValidator, UserRepository userRepository, @Value("${token_expiration}") Long tokenExpiration, JwtTokenManager jwtTokenManager, TotpManager totpManager) {
        this.emailValidator = emailValidator;
        this.userRepository = userRepository;
        this.tokenExpiration = tokenExpiration;
        this.jwtTokenManager = jwtTokenManager;
        this.totpManager = totpManager;
    }

    public ResponseEntity<? extends ResponseDto> store(CreateUserRequestDto dto) {

        if (dto.username.isEmpty()) {
            return new ResponseEntity<>(new ErrorResponseDto("Username is required"), HttpStatus.BAD_REQUEST);
        }
        if (dto.password.isEmpty()) {
            return new ResponseEntity<>(new ErrorResponseDto("Password is required"), HttpStatus.BAD_REQUEST);
        }
        if (dto.email.isEmpty()) {
            return new ResponseEntity<>(new ErrorResponseDto("Email is required"), HttpStatus.BAD_REQUEST);
        }

        boolean usernameExist = userRepository.findByUsername(dto.username).isPresent();
        if (usernameExist) {
            ErrorResponseDto error = new ErrorResponseDto("Username is already taken");
            return new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }

        if (dto.username.length() < 4) {
            ErrorResponseDto error = new ErrorResponseDto("Username must be at least 4 characters long");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        if (dto.password.length() < 8) {
            ErrorResponseDto error = new ErrorResponseDto("Password must be at least 8 characters long");
            return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
        }

        boolean isValidEmail = emailValidator.isValid(dto.email);
        if (!isValidEmail) {
            ErrorResponseDto error = new ErrorResponseDto("Invalid email");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        //if (dto.mfa) {
            String secret = totpManager.generateSecret();
       // }

        User user = new User(dto, tokenExpiration, secret);
        userRepository.save(user);
        UserResponseDto userResponseDto = new UserResponseDto(user, totpManager.getUriForImage(user.getSecret()));

        boolean userCreated = userRepository.findByUsername(dto.username).isPresent();
        if (!userCreated) {
            ErrorResponseDto error = new ErrorResponseDto("Unknown error");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        /*URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/register/{username}")
                .buildAndExpand(user.getUsername()).toUri();*/

        return new ResponseEntity<>(userResponseDto,HttpStatus.CREATED);

       /* return ResponseEntity
                .created(location)
                .body(userResponseDto);*/
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
