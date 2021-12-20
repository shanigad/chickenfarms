package com.chickenfarms.escalationmanagement.model.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "customer_in_ticket")
public class CustomerInTicket {

  @Id
  @Column(name = "customer_id")
  private Long customerId;

  @Column(name = "ticket_id")
  private Long ticketId;

  @Column(name = "added_date")
  private Date addedDate;


  public CustomerInTicket() {
  }

  public CustomerInTicket(Long customerId, Long ticketId, Date addedDate) {
    this.customerId = customerId;
    this.ticketId = ticketId;
    this.addedDate = addedDate;
  }

}
