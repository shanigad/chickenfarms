package com.chickenfarms.escalationmanagement.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.I_AM_A_TEAPOT)
@Getter
public class ChangeStatusException extends RuntimeException{

  private String status;
  private String reason;

  public ChangeStatusException(String status, String reason) {
    super(String.format("Can't move Ticket to status %s because %s", status, reason));
    this.status = status;
    this.reason = reason;
  }
}
