package com.chickenfarms.escalationmanagement.repository;

import com.chickenfarms.escalationmanagement.model.entity.CustomerTicket;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
  public interface CustomerTicketRepository extends JpaRepository<CustomerTicket, Integer> {
  List<CustomerTicket> findCustomerInTicketByCustomerTicketId_Ticket(Ticket ticket);
  List<CustomerTicket> findCustomerInTicketByCustomerTicketId_CustomerId(Long customerId);
  List<CustomerTicket> findCustomerInTicketByCustomerTicketId_TicketAndCustomerId(Ticket ticket, Long customerId);

}
