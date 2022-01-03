package com.chickenfarms.escalationmanagement.model.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;
  @Column(name = "created_by")
  private String createdBy;
  @Column(name = "created_date")
  @CreatedDate
  private Date createdDate;
  @Column(name = "content")
  private String content;
  @ManyToOne
  @JoinColumn(name="ticket_id", nullable=false)
  private Ticket ticket;
}
