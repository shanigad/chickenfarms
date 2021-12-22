package com.chickenfarms.escalationmanagement.model.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
public class CustomerTicketId implements Serializable {
  private static final long serialVersionUID = -6810831695831606907L;
  Long customerId;
  @ManyToOne
  @JoinColumn(name = "ticket_id")
  Ticket ticket;

  public CustomerTicketId(Long customerId, Ticket ticket) {
    this.customerId = customerId;
    this.ticket = ticket;
  }

  public CustomerTicketId() {

  }

}
