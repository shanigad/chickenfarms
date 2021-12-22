package com.chickenfarms.escalationmanagement.model.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "customer_in_ticket")
public class CustomerInTicket {


  @EmbeddedId
  CustomerTicketId id;
//  // TODO replace this column with a combination of customer+ticket
//  @Id
//  @GeneratedValue(strategy= GenerationType.AUTO)
//  @Column(name = "id", nullable = false)
//  private Long id;

//  @Column(name = "customer_id")
//  private Long customerId;
//
//  @ManyToOne
//  @JoinColumn(name="ticket_id", nullable=false)
//  private Ticket ticket;

  @Column(name = "added_date")
  private Date addedDate;

  public CustomerInTicket() {
  }

  public CustomerInTicket(CustomerTicketId customerTicketId, Date addedDate) {
    this.id = customerTicketId;
    this.addedDate = addedDate;
  }


}

