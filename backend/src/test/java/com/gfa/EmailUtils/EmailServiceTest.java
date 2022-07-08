package com.gfa.EmailUtils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;

import javax.mail.MessagingException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {EmailService.class, EmailConfiguration.class})
@ContextConfiguration
class EmailServiceTest {
  @Autowired private EmailService emailService;

  @Test
  void sendPlainEmail() {
    emailService.sendPlainEmail(
        "klarakutlakova@seznam.cz",
        "mar.gazik.mg@gmail.com",
        "testMail",
        "Zdraví Vás Gregor Gregorovič");
  }

  @Test
  void sendHtmlEmail() throws MessagingException {

    emailService.sendHtmlEmail(
        "klarakutlakova@seznam.cz",
        "mar.gazik.mg@gmail.com",
        "Akcioska",
        "Zdraví Vás Gregor Gregorovič");
  }
}
