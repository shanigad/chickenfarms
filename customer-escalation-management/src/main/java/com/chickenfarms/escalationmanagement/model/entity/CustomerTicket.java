package com.chickenfarms.escalationmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customer_in_ticket")
@IdClass(CustomerTicketKey.class)
public class CustomerTicket implements Serializable {

//  @EmbeddedId
//  CustomerTicketKey customerTicketKey;
  @Id
  @Column(name = "customer_id")
  Long customerId;

  @Id
  @ManyToOne
  @JoinColumn(name = "ticket_id")
//  @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
  Ticket ticket;

  @Column(name = "added_date")
  private Date addedDate;

  public CustomerTicket() {
  }

  public CustomerTicket(Long customerId, Ticket ticket, Date addedDate) {
    this.customerId = customerId;
    this.ticket = ticket;
    this.addedDate = addedDate;
  }

  //  public CustomerTicket(CustomerTicketKey customerTicketKey, Date addedDate) {
//    this.customerTicketKey = customerTicketKey;
//    this.addedDate = addedDate;
//  }


}

