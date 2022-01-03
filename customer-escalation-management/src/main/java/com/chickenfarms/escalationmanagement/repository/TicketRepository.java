package com.chickenfarms.escalationmanagement.repository;

import com.chickenfarms.escalationmanagement.model.entity.Problem;
import com.chickenfarms.escalationmanagement.model.entity.RootCause;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TicketRepository extends JpaRepository<Ticket, Long> {
  Ticket findTicketByProblemAndProviderAndCreatedBy(Problem problem, String provider, String createdBy);
  Optional<Ticket> findTicketByProviderAndRootCause(String provider, RootCause rootCause);
//  @Modifying
//  @Query("update Ticket t set t.status= ?1, t.isResolved= ?2 where t.id= ?3")
//  int setStatusAndIsResolvedForTicket(String status, boolean isResolved, Long id);
//  @Modifying
//  @Query("update Ticket t set t.status= ?1, t.rootCause= ?2 where t.id= ?3")
//  Ticket setStatusForTicket(String status, RootCause  rootCause, Long id);
}
