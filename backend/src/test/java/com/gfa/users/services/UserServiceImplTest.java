package com.gfa.users.services;

import static org.assertj.core.api.Assertions.assertThat;
import com.gfa.users.dtos.EmailRequestDto;
import com.gfa.users.dtos.ResponseDto;
import com.gfa.users.models.User;
import com.gfa.users.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl(userRepository);


    @Test
    public void test_method() {

        User user1 = new User("alex@gmail.com");
        User user2 = new User("alex2gmail.com");
        User user3 = new User("alex3@gmail.com",new Date());

        List<User> users = Arrays.asList(user1,user2,user3);
        Iterable<User> allUsers = userRepository.saveAll(users);

        //providing knowledge
        Mockito
                .when(userService.resendVerificationEmail(any(EmailRequestDto.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        EmailRequestDto email = new EmailRequestDto("alex@gmail.com");
        ResponseEntity<? extends ResponseDto> re = userService.resendVerificationEmail(email);
        
        assertThat(re.getStatusCode()).isEqualTo(200);
        }
}















