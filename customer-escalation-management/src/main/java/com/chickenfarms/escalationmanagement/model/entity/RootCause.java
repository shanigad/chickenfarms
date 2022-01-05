package com.chickenfarms.escalationmanagement.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="root_cause")
public class RootCause {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  @Column(name = "rc_id", nullable = false)
  private Long id;
  @Column(name = "name", nullable = false)
  private String name;

}
