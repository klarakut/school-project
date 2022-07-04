package com.gfa.users.services;

import com.gfa.users.EmailConfiguration;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class EmailServiceTest {

  @Test
  void sendSimpleMessage() {

    EmailConfiguration emailConfiguration = new EmailConfiguration();

    EmailService emailService = new EmailService(emailConfiguration);
    emailService.sendSimpleMessage("martin.gazik.mg@gmail.com","random@gmail.com","Unit test","test");



  }
}
