package com.gfa.common.services;

public interface EmailService {

  void sendPlainEmail(String to, String from, String subject, String text);

  void sendHtmlEmail(String to, String from, String subject, String text);
}
