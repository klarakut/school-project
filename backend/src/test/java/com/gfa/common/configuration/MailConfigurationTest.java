package com.gfa.common.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Properties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
class MailConfigurationTest {

  @Autowired private JavaMailSender mailSender;

  @Autowired private Environment environment;

  @Test
  void can_create_mail_sender() {
    assertNotNull(mailSender);
  }

  @Test
  void configures_smtp_mail_sender_based_on_env_settings() {

    JavaMailSenderImpl sender = (JavaMailSenderImpl) mailSender;
    assertEquals(environment.getProperty("spring.mail.host"), sender.getHost());
    assertEquals(environment.getProperty("spring.mail.port"), String.valueOf(sender.getPort()));
    assertEquals(environment.getProperty("spring.mail.username"), sender.getUsername());
    assertEquals(environment.getProperty("spring.mail.password"), sender.getPassword());

    Properties properties = sender.getJavaMailProperties();
    assertEquals("smtp", properties.getProperty("mail.transport.protocol"));
    assertEquals("true", properties.getProperty("mail.smtp.auth"));
    assertEquals("true", properties.getProperty("mail.smtp.starttls.enable"));
    assertEquals("true", properties.getProperty("mail.debug"));
  }
}
