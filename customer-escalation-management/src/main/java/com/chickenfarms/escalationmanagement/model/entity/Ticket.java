package com.chickenfarms.escalationmanagement.model.entity;

import com.chickenfarms.escalationmanagement.enums.Status;
import com.chickenfarms.escalationmanagement.model.dto.CreatedTicketRequest;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@Entity
@Table(name="Ticket")
public class Ticket {

  @Id
  @GeneratedValue
  private long id;
  private String description;
  private String problem;
  private String createdBy;
//  @CreatedDate
  private Date createdDate;
//  @LastModifiedDate
  private Date lastModifiedDate;
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
