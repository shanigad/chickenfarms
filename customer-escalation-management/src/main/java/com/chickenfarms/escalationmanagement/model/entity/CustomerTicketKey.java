package com.chickenfarms.escalationmanagement.model.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class CustomerTicketKey implements Serializable {

  Long customerId;
//  @ManyToOne
//  @JoinColumn(name = "ticket_id")
//  @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
  Long ticket;

  public CustomerTicketKey(Long customerId, Long ticketId) {
    this.customerId = customerId;
    this.ticket = ticketId;
  }

  public CustomerTicketKey() {

  }

}
