package com.gfa.users.controllers;

import com.gfa.common.dtos.EmailRequestDto;
import com.gfa.common.dtos.ResponseDto;
import com.gfa.users.services.EmailVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailVerificationController {

  private final EmailVerificationService service;

  @Autowired
  public EmailVerificationController(EmailVerificationService service) {
    this.service = service;
  }

  @GetMapping("/email/verify/{token}")
  public ResponseEntity<? extends ResponseDto> verify(
      @PathVariable(required = false) String token) {
    return new ResponseEntity<>(service.verify(token), HttpStatus.OK);
  }

  @PostMapping("/email/resend")
  public ResponseEntity<? extends ResponseDto> resend(
      @RequestBody(required = false) EmailRequestDto dto) {
    return new ResponseEntity<>(service.resend(dto), HttpStatus.OK);
  }
}
