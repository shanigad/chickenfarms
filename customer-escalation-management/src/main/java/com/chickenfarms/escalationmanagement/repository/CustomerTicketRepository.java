package com.chickenfarms.escalationmanagement.repository;

import com.chickenfarms.escalationmanagement.model.entity.CustomerTicket;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
  public interface CustomerTicketRepository extends JpaRepository<CustomerTicket, Integer> {
  List<CustomerTicket> findCustomerTicketByCustomerTicketKey_Ticket(Ticket ticket);
  List<CustomerTicket> findCustomerTicketByCustomerTicketKey_CustomerId(Long customerId);
  List<CustomerTicket> findCustomerTicketByCustomerTicketKey_TicketAndCustomerTicketKey_CustomerId(Ticket ticket, Long customerId);

}
