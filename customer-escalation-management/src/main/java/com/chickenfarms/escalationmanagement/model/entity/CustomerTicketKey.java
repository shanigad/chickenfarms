package com.chickenfarms.escalationmanagement.model.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerTicketKey implements Serializable {

  Long customerId;
  Long ticket;

  public CustomerTicketKey(Long customerId, Long ticketId) {
    this.customerId = customerId;
    this.ticket = ticketId;
  }

  public CustomerTicketKey() {

  }

}
