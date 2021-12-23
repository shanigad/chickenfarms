package com.chickenfarms.escalationmanagement.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class TicketCreationRequest {
  @NotBlank(message = "provider must not be empty")
  private String provider;
  @NotBlank(message = "description must not be empty")
  private String description;
  @NotNull(message = "problem number must not be empty")
  private int problem;
  @NotBlank(message = "created by number must not be empty")
  private String createdBy;
  @NotNull(message = "customers list must contain at least 1 customer")
  @Size(min=1, message ="customers list must contain at least 1 customer" )
  private Long[] customers;

  public TicketCreationRequest() {
  }
}
