package com.chickenfarms.escalationmanagement.model.dto;

import com.chickenfarms.escalationmanagement.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketFilterRequest {

  private Status status;
  private String tag;
  private String provider;
  private Long problem;
  private String rootCause;
}
