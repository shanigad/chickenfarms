package com.chickenfarms.escalationmanagement.repository;

import com.chickenfarms.escalationmanagement.model.entity.Problem;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
  Ticket findTicketByProblemAndProviderAndCreatedBy(Problem problem, String provider, String createdBy);
}
