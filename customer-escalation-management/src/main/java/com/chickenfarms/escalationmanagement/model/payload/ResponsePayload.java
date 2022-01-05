package com.chickenfarms.escalationmanagement.model.payload;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ResponsePayload {
  private String message;
  private List<Object> context;

  public ResponsePayload(String message, Object context) {
    this.message = message;
    this.context = new ArrayList<>();
    this.context.add(context);
  }
}
