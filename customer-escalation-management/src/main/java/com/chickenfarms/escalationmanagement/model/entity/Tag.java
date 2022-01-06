package com.chickenfarms.escalationmanagement.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashSet;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tag")
public class Tag {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "tag_id")
  private Long id;
  @Column(name = "name", nullable = false)
  private String name;
  @JsonIgnore
  @ManyToMany(mappedBy = "tags")
  private Set<Ticket> tickets = new HashSet<>();
}
