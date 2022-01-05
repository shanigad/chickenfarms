package com.chickenfarms.escalationmanagement.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CloseTicketRequest {
  @NotBlank(message = "Closing comment must not be empty")
  private String closingComment;
  @NotNull(message = "isResolved must be boolean")
  private boolean isResolved;


  public CloseTicketRequest() {
  }
}
