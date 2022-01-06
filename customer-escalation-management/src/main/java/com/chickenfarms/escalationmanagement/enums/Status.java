package com.chickenfarms.escalationmanagement.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;

public enum Status {
  CREATED("Created"),
  READY("Ready"),
  CLOSED("Closed"),
  IN_PROGRESS("Progress"),
  RECONCILED("Reconciled");


  private String status;
  Status(String status) {
    this.status=status;
  }
  public String getStatus() {
    return status;
  }

  public static Status fromValue(String value) {
    for (Status edgeType : values()) {
      if ( edgeType.status.equalsIgnoreCase(value) ) {
        return edgeType;
      }
    }
    throw new IllegalArgumentException("Unknown Enum type " + value + ". Allowed values are (case insensitive): " + Arrays.toString(values()));

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
