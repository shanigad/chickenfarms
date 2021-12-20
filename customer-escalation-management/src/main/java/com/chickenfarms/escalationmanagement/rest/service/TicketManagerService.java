package com.chickenfarms.escalationmanagement.rest.service;

import com.chickenfarms.escalationmanagement.model.dto.CreatedTicketRequest;
import com.chickenfarms.escalationmanagement.model.entity.CustomerInTicket;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.repository.CustomerInTicketRepository;
import com.chickenfarms.escalationmanagement.repository.TicketManagerRepository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;

public class TicketManagerService {

  @Autowired
  TicketManagerRepository ticketManagerRepository;

  @Autowired
  CustomerInTicketRepository customerInTicketRepository;

  @Autowired
  public TicketManagerService() {
  }


  public Long submitTicket(CreatedTicketRequest createdTicket){
    Ticket ticket = createTicket(createdTicket);
    attachCustomersToTicket(createdTicket.getCustomers(), ticket.getId(), ticket.getCreatedDate());

      return ticket.getId();
  }

  private void attachCustomersToTicket(Long[] customers, long ticketId, Date createdDate) {
    CustomerInTicket customerInTicket;
    for (Long c: customers) {
      customerInTicket = new CustomerInTicket(c, ticketId, createdDate);
      customerInTicketRepository.save(customerInTicket);
    }
  }

  private Ticket createTicket(CreatedTicketRequest createdTicket) {
    Ticket ticket = new Ticket(createdTicket);
    ticket = ticketManagerRepository.save(ticket);
    ticketManagerRepository.flush();
    return ticket;
  }
}
