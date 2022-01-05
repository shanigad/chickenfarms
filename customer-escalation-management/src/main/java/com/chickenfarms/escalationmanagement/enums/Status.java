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

  public static boolean isClosed(String status){
    return CLOSED.getStatus().equals(status);
  }

  public static boolean isReady(String status){
    return READY.getStatus().equals(status);
  }

  public static boolean isCreated(String status){
    return CREATED.getStatus().equals(status);
  }

  public static boolean isReconciled(String status){
    return RECONCILED.getStatus().equals(status);
  }
}
