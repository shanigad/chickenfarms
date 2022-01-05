package com.chickenfarms.escalationmanagement.model.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TicketUpdateRequest {

  @NotNull
  String description;
  private int problem;
}
