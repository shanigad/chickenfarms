package com.chickenfarms.escalationmanagement.model.dto;

import com.chickenfarms.escalationmanagement.model.entity.Comment;
import com.chickenfarms.escalationmanagement.model.entity.Problem;
import com.chickenfarms.escalationmanagement.model.entity.RootCause;
import com.chickenfarms.escalationmanagement.model.entity.Tag;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;


@Getter
public class TicketResponse {
  private long number;
  private String description;
  private String provider;
  private String createdBy;
  private Date createdDate;
  private Date lastModifiedDate;
  private String status;
  private String problem;
  private String rootCause;
  private boolean isResolved;
  private Date closedDate;

  private Set<String> tags;
//  private Set<Long> customers;
  private List<String> comments;

  public TicketResponse(Ticket ticket) {
    this.number = ticket.getId();
    this.description = ticket.getDescription();
    this.provider = ticket.getProvider();
    this.createdBy = ticket.getCreatedBy();
//    this.createdDate = ticket.getCreatedDate();
    this.lastModifiedDate = ticket.getLastModifiedDate();
    this.status = ticket.getStatus();
    this.isResolved = ticket.isResolved();
    this.closedDate = ticket.getClosedDate();
    setProblem(ticket.getProblem());
    setTags(ticket.getTags());
//    setCustomers(ticket.getCustomers());
    setComments(ticket.getComments());
    setRootCause(ticket.getRootCause());
  }


  private void setTags(Set<Tag> tags) {
    this.tags = new HashSet<>();
     tags.stream().forEach(tag -> this.tags.add(tag.getName()));
  }
//  public void setCustomers(Set<CustomerTicket> customers) {
//    this.customers = new HashSet<>();
//     customers.stream().forEach(c -> this.customers.add(c.getCustomerId()));
//  }

  private void setComments(List<Comment> comments){
    this.comments = new ArrayList<>();
    comments.stream().forEach(comment -> this.comments.add(comment.toString()));
  }
  private void setProblem(Problem problem) {
    this.problem = problem == null? "": problem.getName();
  }

  private void setRootCause(RootCause rc) {
    this.rootCause = rc == null? "": rc.getName();
  }

}
