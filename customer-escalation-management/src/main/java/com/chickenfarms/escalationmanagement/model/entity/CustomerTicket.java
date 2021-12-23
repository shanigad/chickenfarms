package com.chickenfarms.escalationmanagement.model.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "customer_in_ticket")
public class CustomerTicket {

  @EmbeddedId
  CustomerTicketKey customerTicketKey;

  @Column(name = "added_date")
  private Date addedDate;

  public CustomerTicket() {
  }

  public CustomerTicket(CustomerTicketKey customerTicketKey, Date addedDate) {
    this.customerTicketKey = customerTicketKey;
    this.addedDate = addedDate;
  }


}

