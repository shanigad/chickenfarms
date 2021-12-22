package com.chickenfarms.escalationmanagement.model.dto;

import lombok.Data;

@Data
public class CreatedTicketRequest{
  private String provider;
  private String description;
  private int problem;
  private String createdBy;
  private Long[] customers;
}
