package com.chickenfarms.escalationmanagement.model.dto;

import lombok.Data;

@Data
public class CreatedTicketRequest{
  private String description;
  private String problem;
  private String createdBy;
  private Long[] customers;
}
