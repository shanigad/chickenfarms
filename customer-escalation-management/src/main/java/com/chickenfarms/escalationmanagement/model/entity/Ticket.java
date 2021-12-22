package com.chickenfarms.escalationmanagement.model.entity;

import com.chickenfarms.escalationmanagement.enums.Status;
import com.chickenfarms.escalationmanagement.model.dto.CreatedTicketRequest;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

@Data
@Entity
@Table(name="ticket")
public class Ticket {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  @Column(name = "ticket_id")
  private long id;
  @Column(name = "description")
  private String description;
  @Column(name = "provider_id")
  private String provider;
//  @Column(name = "problem")
//  private String problem;
  @Column(name = "created_by")
  private String createdBy;
  @Column(name = "created_date")
  @CreatedDate
  private Date createdDate;
  @Column(name = "last_modified_date")
  private Date lastModifiedDate;
  @Column(name = "status")
  private Status status;
  @Column(name = "closed_date")
  private Date closedDate;
  @Column(name = "is_resolved")
  private boolean isResolved;
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tickets")
  private Set<Tag> tags;
  @ManyToOne
  @JoinColumn(name="id", nullable=false)
  private Problem problem;


  public Ticket(CreatedTicketRequest createdTicket,   Problem problem){
    description = createdTicket.getDescription();
    createdBy = createdTicket.getCreatedBy();
    provider = createdTicket.getProvider();
    this.problem = problem;
    status = Status.CREATED;
    createdDate = new Date();
    lastModifiedDate = createdDate;
    isResolved = false;
  }


  public Ticket() {

  }
}
