package com.gfa.common.services;

import com.gfa.common.exceptions.HtmlSendException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImp implements EmailService {

  private final JavaMailSender javaMailSender;

  @Autowired
  public EmailServiceImp(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  @Override
  public void sendPlainEmail(String to, String from, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text);
    message.setFrom(from);
    javaMailSender.send(message);
  }

  @Override
  public void sendHtmlEmail(String to, String from, String subject, String text) {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "charset=UTF-8");
    try {
      mimeMessage.setContent(text, "text/html; charset=UTF-8");
      mimeMessageHelper.setFrom(from);
      mimeMessageHelper.setTo(to);
      mimeMessageHelper.setSubject(subject);
      javaMailSender.send(mimeMessage);
    } catch (MessagingException e) {
      throw new HtmlSendException();
    }
  }
}
