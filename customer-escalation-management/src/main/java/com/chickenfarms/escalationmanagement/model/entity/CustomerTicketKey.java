package com.chickenfarms.escalationmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
public class CustomerTicketKey implements Serializable {
  @Column(name = "customer_id")
  Long customerId;
  @ManyToOne
  @JoinColumn(name = "ticket_id")
  @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
  Ticket ticket;

  public CustomerTicketKey(Long customerId, Ticket ticket) {
    this.customerId = customerId;
    this.ticket = ticket;
  }

  public CustomerTicketKey() {

  }

}
