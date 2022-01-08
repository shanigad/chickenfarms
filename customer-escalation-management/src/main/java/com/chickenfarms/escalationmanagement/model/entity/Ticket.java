package com.chickenfarms.escalationmanagement.model.entity;

import com.chickenfarms.escalationmanagement.enums.Status;
import com.chickenfarms.escalationmanagement.model.payload.TicketCreationRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="ticket")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ticket {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "ticket_id")
  private long id;
  @Column(name = "description")
  private String description;
  @Column(name = "provider_id")
  private String provider;
  @Column(name = "created_by")
  private String createdBy;
  @Column(name = "creation_date")
  private Date creationDate;
  @Column(name = "last_modified_date")
  private Date lastModifiedDate;
  @Column(name = "status")
  private String status;
  @Column(name = "impact")
  @Min(value=0)
  private int impact;
  @Column(name = "closed_date")
  private Date closedDate;
  @Column(name = "is_resolved")
  private boolean isResolved;
  @Column(name = "sla")
  private int sla;
  @Column(name = "sla_hour")
  private int slaHour;
  @Column(name = "is_oomlette")
  private boolean isOomlette;
  @Column(name = "grade")
  private int grade;

  @ManyToOne
  @JoinColumn(name="problem_id")
  private Problem problem;


  @ManyToOne
  @JoinColumn(name="rc_id")
  private RootCause rootCause;

//  @OneToMany(mappedBy = "customerId",
//      cascade = CascadeType.ALL,
//      orphanRemoval = true)
//  private Set<CustomerTicket> customers = new HashSet<>();

  @ManyToMany
  @JoinTable(name = "tags_on_tickets",
      joinColumns =@JoinColumn(name="ticket_id"),
      inverseJoinColumns=@JoinColumn(name = "tag_id"))
  private Set<Tag> tags = new HashSet<>();

  public Ticket(TicketCreationRequest createdTicket, Problem problem){
    description = createdTicket.getDescription();
    createdBy = createdTicket.getCreatedBy();
    provider = createdTicket.getProvider();
    this.problem = problem;
    status = Status.CREATED.getStatus();
    creationDate = new Date();
    lastModifiedDate = creationDate;
    isResolved = false;
    isOomlette = false;
    impact = createdTicket.getCustomers().length;
  }

  public Ticket(Ticket ticket) {
    description = ticket.getDescription();
    createdBy = ticket.getCreatedBy();
    provider = ticket.getProvider();
    this.problem = ticket.getProblem();
    status = Status.CREATED.getStatus();
    setTags(ticket.getTags());
    creationDate = new Date();
    lastModifiedDate = creationDate;
    isResolved = ticket.isResolved();
    isOomlette = ticket.isOomlette();
    sla = ticket.getSla();
  }

  public void setTags(Set<Tag> tags) {
    tags.stream().forEach(tag -> this.tags.add(tag));
  }
  public void addTag(Tag tag){
    tags.add(tag);
  }
  public void addTags(Set<Tag> tags){
    this.tags.addAll(tags);
  }
  public void slaTicking(){
    sla =-1;
  }
  public void reduceImpact(){
    impact -=1;
}
  public void increaseImpact(){
    impact +=1;
}



}
