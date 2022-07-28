package com.gfa.users.controllers;


import com.gfa.common.dtos.ErrorResponseDto;
import com.gfa.users.exceptions.InvalidIdException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(InvalidIdException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleInvalidIdException() {
    return "Invalid Id";
  }

}
