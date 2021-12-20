package com.chickenfarms.escalationmanagement.model.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CustomerInTicket")
public class CustomerInTicket {

  @Id
  private Long customerId;

  @Id
  private Long ticketId;

  private Date addedDate;


  public CustomerInTicket() {
  }

  public CustomerInTicket(Long customerId, Long ticketId, Date addedDate) {
    this.customerId = customerId;
    this.ticketId = ticketId;
    this.addedDate = addedDate;
  }

  public Date getAddedDate() {
    return addedDate;
  }

  public void setAddedDate(Date addedDate) {
    this.addedDate = addedDate;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long id) {
    this.customerId = customerId;
  }

  public Long getTicketId() {
    return customerId;
  }

  public void setTicketId(Long id) {
    this.customerId = customerId;
  }
}
