package com.chickenfarms.escalationmanagement.repository;

import com.chickenfarms.escalationmanagement.model.entity.CustomerTicket;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
  public interface CustomerTicketRepository extends JpaRepository<CustomerTicket, Integer> {
  List<CustomerTicket> findCustomerTicketByTicket(Ticket ticket);
  List<CustomerTicket> findCustomerTicketByCustomerId(Long customerId);
  Optional<CustomerTicket> findCustomerTicketByTicketAndCustomerId(Ticket ticket, Long customerId);
  @Transactional
  void deleteByTicketAndCustomerId(Ticket ticket, Long customerId);

}
