package com.chickenfarms.escalationmanagement.model.dto;

import com.chickenfarms.escalationmanagement.model.entity.CustomerTicket;
import com.chickenfarms.escalationmanagement.model.entity.Problem;
import com.chickenfarms.escalationmanagement.model.entity.Tag;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;


@Data
public class TicketDto {
  private long id;
  private String description;
  private String provider;
  private String createdBy;
  private Date createdDate;
  private Date lastModifiedDate;
  private String status;
  private String problem;

  private Set<String> tags;
  private Set<Long> customers;

  public TicketDto(Ticket ticket) {
    this.id = ticket.getId();
    this.description = ticket.getDescription();
    this.provider = ticket.getProvider();
    this.createdBy = ticket.getCreatedBy();
    this.createdDate = ticket.getCreatedDate();
    this.lastModifiedDate = ticket.getLastModifiedDate();
    this.status = ticket.getStatus();
    setProblem(ticket.getProblem());
    setTags(ticket.getTags());
    setCustomers(ticket.getCustomers());
  }


  public void setTags(Set<Tag> tags) {
    this.tags = new HashSet<>();
     tags.stream().forEach(tag -> this.tags.add(tag.getName()));
  }
  public void setCustomers(Set<CustomerTicket> customers) {
    this.customers = new HashSet<>();
     customers.stream().forEach(c -> this.customers.add(c.getCustomerId()));
  }

  public void setProblem(Problem problem) {
    this.problem = problem == null? "": problem.getName();
  }
}
