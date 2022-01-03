package com.chickenfarms.escalationmanagement.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tag")
public class Tag {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  @Column(name = "tag_id", nullable = false)
  private Long id;
  @Column(name = "name", nullable = false)
  private String name;
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(name = "TAGS_IN_TICKETS",
      joinColumns = { @JoinColumn(name="tag_id")},
      inverseJoinColumns={@JoinColumn(name = "ticket_id")})
  @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
  private Set<Ticket> tickets;
}
