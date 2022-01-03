package com.chickenfarms.escalationmanagement.model.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name="problem")
public class Problem {

  @Id
  @Column(name = "id", nullable = false)
  private int id;
  @Column(name = "name", nullable = false)
  private String name;

  public Problem(int id, String name) {
    this.id = id;
    this.name = name;
  }
}
