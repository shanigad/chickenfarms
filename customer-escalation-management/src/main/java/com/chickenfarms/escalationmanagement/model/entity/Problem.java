package com.chickenfarms.escalationmanagement.model.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="problem")
public class Problem {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "problem_id")
  private Long id;
  @Column(name = "name", nullable = false)
  private String name;

}
