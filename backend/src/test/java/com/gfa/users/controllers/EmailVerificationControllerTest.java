package com.gfa.users.controllers;

import com.gfa.users.services.EmailVerificationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;

@WebMvcTest(
    value = EmailVerificationController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
class EmailVerificationControllerTest {

  @MockBean
  UserDetailsService userDetailsService;

  @MockBean
  EmailVerificationService service;

  @Test
  void verify_with_no_or_empty_token_returns_an_error() {}

  @Test
  void verify_with_invalid_token_returns_an_error() {}

  @Test
  void verify_with_valid_token_returns_response_status_dto() {}

  @Test
  void verify_with_an_expired_token_returns_an_error() {}

  @Test
  void verify_with_an_already_verified_email_returns_an_error() {}

  @Test
  void resend_with_request_an_existing_email_returns_status_response_dto() {}

  @Test
  void resend_with_request_non_existing_email_returns_an_error() {}

  @Test
  void resend_with_no_body_returns_an_error() {}
}
