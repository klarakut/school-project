package com.gfa.EmailUtils;

import com.gfa.common.configuration.EmailConfiguration;
import com.gfa.common.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.mail.MessagingException;

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
