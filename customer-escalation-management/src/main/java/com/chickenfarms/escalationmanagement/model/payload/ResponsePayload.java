package com.chickenfarms.escalationmanagement.model.payload;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponsePayload {
  private String message;
  private List<Object> context;

  public ResponsePayload(String message, Object context) {
    this.message = message;
    this.context = new ArrayList<>();
    this.context.add(context);
  }
}
