package com.chickenfarms.escalationmanagement.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TicketCreationRequest {
  @NotBlank(message = "provider must not be empty")
  private final String provider;
  @NotBlank(message = "description must not be empty")
  private final String description;
  @NotNull(message = "problem number must not be empty")
  private final int problem;
  @NotBlank(message = "created by must not be empty")
  private final String createdBy;
  @NotNull(message = "customers list must contain at least 1 customer")
  @Size(min=1, message ="customers list must contain at least 1 customer" )
  private final Long[] customers;
}
