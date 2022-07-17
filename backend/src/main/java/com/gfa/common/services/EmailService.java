package com.gfa.common.services;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
  void send(SimpleMailMessage email);
}
