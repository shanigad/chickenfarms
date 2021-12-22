package com.chickenfarms.escalationmanagement.model.entity;

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

import java.util.Set;
    import javax.persistence.CascadeType;
    import javax.persistence.Column;
    import javax.persistence.Entity;
    import javax.persistence.FetchType;
    import javax.persistence.Id;
    import javax.persistence.JoinColumn;
    import javax.persistence.JoinTable;
    import javax.persistence.ManyToMany;
    import javax.persistence.Table;
    import lombok.Data;

@Data
@Entity
@Table(name="root_cause")
public class RootCause {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  @Column(name = "rc_id", nullable = false)
  private Long id;
  @Column(name = "name", nullable = false)
  private String name;
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(name = "RC_FOR_TICKETS",
      joinColumns = { @JoinColumn(name="rc_id")},
      inverseJoinColumns={@JoinColumn(name = "ticket_id")})
  private Set<Ticket> tickets;
}
