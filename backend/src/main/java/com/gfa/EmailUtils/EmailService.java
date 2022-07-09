package com.gfa.EmailUtils;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

  private final EmailConfiguration emailConfiguration;

  @Autowired
  public EmailService(EmailConfiguration emailConfiguration) {
    this.emailConfiguration = emailConfiguration;
  }

  private JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
    mailSenderImpl.setHost(emailConfiguration.getHost());
    mailSenderImpl.setPort(emailConfiguration.getPort());
    mailSenderImpl.setUsername(emailConfiguration.getUsername());
    mailSenderImpl.setPassword(emailConfiguration.getPassword());
    return mailSenderImpl;
  }

  public void sendPlainEmail(String to, String from, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text);
    message.setFrom(from);
    getJavaMailSender().send(message);
  }

  public void sendHtmlEmail(String to, String from, String subject, String text)
      throws MessagingException {
    MimeMessage mimeMessage = getJavaMailSender().createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "charset=UTF-8");
    mimeMessage.setContent(text, "text/html; charset=UTF-8");
    mimeMessageHelper.setFrom(from);
    mimeMessageHelper.setTo(to);
    mimeMessageHelper.setSubject(subject);
    getJavaMailSender().send(mimeMessage);
  }
}
