package com.chickenfarms.escalationmanagement.exception;

public class ResourceAlreadyExistException extends RuntimeException{
  private String resourceName;
  private String resourceId;


  public ResourceAlreadyExistException(String resourceName, String id) {
    super(String.format("%s already exist with id: '%s'", resourceName, id));
    this.resourceName = resourceName;
    this.resourceId = id;

  }
  public String getResourceName() {
    return resourceName;
  }

  public String resourceId() {
    return resourceId;
  }



}
