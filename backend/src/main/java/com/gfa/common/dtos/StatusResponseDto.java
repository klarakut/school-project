package com.gfa.common.dtos;

public class StatusResponseDto extends ResponseDto {

<<<<<<< HEAD
public class StatusResponseDto extends ResponseDto{
    public String status;
=======
  public final String status;
>>>>>>> develop

  public StatusResponseDto(String status) {
    this.status = status;
  }
}
