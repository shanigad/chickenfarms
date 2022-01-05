package com.chickenfarms.escalationmanagement.model.entity;

import com.chickenfarms.escalationmanagement.model.dto.PostCommentRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  @Column(name = "comment_id", nullable = false)
  private Long id;
  @Column(name = "created_by")
  private String createdBy;
  @Column(name = "created_date")
  private Date createdDate;
  @Column(name = "content")
  private String content;
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name="ticket_id", nullable=false)
  private Ticket ticket;


  public Comment(PostCommentRequest postCommentRequest){
    content = postCommentRequest.getComment();
    createdBy = postCommentRequest.getCreatedBy();
    createdDate = new Date();
  }

  @Override
  public String toString(){
    return getCreatedDate() + " " +getCreatedBy() + ": " + getContent();
  }

}
