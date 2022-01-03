package com.chickenfarms.escalationmanagement.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus( value = HttpStatus.CONFLICT)
public class ResourceAlreadyExistException extends RuntimeException{
  private String resourceName;
  private Long resourceId;


  public ResourceAlreadyExistException(String resourceName, Long id) {
    super(String.format("%s already exist with id: '%s'", resourceName, id));
    this.resourceName = resourceName;
    this.resourceId = id;

  }



}
