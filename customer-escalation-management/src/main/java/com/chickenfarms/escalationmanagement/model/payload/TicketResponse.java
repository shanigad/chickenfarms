package com.chickenfarms.escalationmanagement.model.payload;

import com.chickenfarms.escalationmanagement.model.entity.Tag;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {
  private long ticketNumber;
  private String description;
  private String provider;
  private String createdBy;
//  private Date creationDate;
  private Date lastModifiedDate;
  private String status;
  private String problem;
  private String rootCause;
  private boolean isResolved;
  private Date closedDate;
  private Set<String> tags =  new HashSet<>();
//  private Set<Long> customers;

  public TicketResponse(Ticket ticket) {
    if(ticket == null) return;
    this.ticketNumber = ticket.getId();
    this.description = ticket.getDescription();
    this.provider = ticket.getProvider();
    this.createdBy = ticket.getCreatedBy();
//    this.createdDate = ticket.getCreationDate();
    this.lastModifiedDate = ticket.getLastModifiedDate();
    this.status = ticket.getStatus();
    this.isResolved = ticket.isResolved();
    this.closedDate = ticket.getClosedDate();
    this.problem = ticket.getProblem().getName();
    this.rootCause = ticket.getProblem() == null?null:ticket.getProblem().getName();
    this.rootCause = ticket.getRootCause() == null?null:ticket.getRootCause().getName();
    setTags(ticket.getTags());
//    setCustomers(ticket.getCustomers());
  }


  private void setTags(Set<Tag> tags) {
     tags.stream().forEach(tag -> this.tags.add(tag.getName()));
  }
//  public void setCustomers(Set<CustomerTicket> customers) {
//    this.customers = new HashSet<>();
//     customers.stream().forEach(c -> this.customers.add(c.getCustomerId()));
//  }


}
