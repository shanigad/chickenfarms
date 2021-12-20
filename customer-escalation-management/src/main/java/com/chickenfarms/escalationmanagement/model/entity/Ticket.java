package com.chickenfarms.escalationmanagement.model.entity;

import com.chickenfarms.escalationmanagement.enums.Status;
import com.chickenfarms.escalationmanagement.model.dto.CreatedTicketRequest;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@Entity
@Table(name="ticket")
public class Ticket {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  @Column(name = "id")
  private long id;
  @Column(name = "description")
  private String description;
  @Column(name = "problem")
  private String problem;
  @Column(name = "created_by")
  private String createdBy;
  @Column(name = "created_date")
  @CreatedDate
  private Date createdDate;
  @Column(name = "last_modified_date")
  private Date lastModifiedDate;
  @Column(name = "status")
  private Status status;

  public Ticket() {

  }

  public Ticket(CreatedTicketRequest createdTicket){
    description = createdTicket.getDescription();
    createdBy = createdTicket.getCreatedBy();
    problem = createdTicket.getProblem();
    status = Status.CREATED;
  }


}
