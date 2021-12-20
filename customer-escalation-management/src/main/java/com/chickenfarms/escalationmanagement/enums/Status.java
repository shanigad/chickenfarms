package com.chickenfarms.escalationmanagement.enums;

public enum Status {
  CREATED(1),
  READY(2),
  CLOSED(3),
  RECONCILED(4);


  private int status;
  Status(int status) {
    this.status=status;
  }
  public int getStatus() {
    return status;
  }
}
