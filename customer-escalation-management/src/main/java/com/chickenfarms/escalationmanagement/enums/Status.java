package com.chickenfarms.escalationmanagement.enums;

public enum Status {
  CREATED("Created"),
  READY("Ready"),
  CLOSED("Closed"),
  RECONCILED("Reconciled");


  private String status;
  Status(String status) {
    this.status=status;
  }
  public String getStatus() {
    return status;
  }
}
