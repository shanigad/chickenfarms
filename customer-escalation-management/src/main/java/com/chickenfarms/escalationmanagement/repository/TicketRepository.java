package com.chickenfarms.escalationmanagement.repository;

import com.chickenfarms.escalationmanagement.model.entity.Problem;
import com.chickenfarms.escalationmanagement.model.entity.RootCause;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TicketRepository extends JpaRepository<Ticket, Long> {
  Ticket findTicketByProblemAndProviderAndCreatedBy(Problem problem, String provider, String createdBy);
  Optional<Ticket> findTicketByProviderAndRootCause(String provider, RootCause rootCause);

  @Query(value = "SELECT t FROM Ticket t WHERE (:status is null or t.status = :status) and (:provider is null"
      + " or t.provider = :provider) and (:problem is null or t.problem = :problem)")
  Page<Ticket> getAllByStatusAndProviderAndProblem(String status, String provider, Problem problem, Pageable pageable);
}
