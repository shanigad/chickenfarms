package com.chickenfarms.escalationmanagement.rest.service;

import com.chickenfarms.escalationmanagement.exception.ResourceNotFoundException;
import com.chickenfarms.escalationmanagement.model.dto.TicketCreationRequest;
import com.chickenfarms.escalationmanagement.model.entity.Problem;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.repository.ProblemRepository;
import com.chickenfarms.escalationmanagement.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketManagerService {

  @Autowired
  TicketRepository ticketRepository;

  @Autowired
  CustomerService customerService;

  @Autowired
  ProblemRepository problemRepository;


  public Long submitTicket(TicketCreationRequest createdTicket){
    Problem problem = problemRepository.findById(createdTicket.getProblem()).
        orElseThrow(()-> new ResourceNotFoundException("Problem", "id", String.valueOf(createdTicket.getProblem())));
    // TODO check if ticket with problem+Customer+provider +description? exist before creation
    Ticket ticket = createTicket(createdTicket, problem);
    customerService.attachCustomersToTicket(createdTicket.getCustomers(), ticket, ticket.getCreatedDate());
    return ticket.getId();
  }

  public Ticket getTicket(Long id){
    return ticketRepository.getById(id);
  }


  private Ticket createTicket(TicketCreationRequest createdTicket, Problem problem) {
    Ticket ticket = new Ticket(createdTicket, problem);
    ticket = ticketRepository.save(ticket);
    ticketRepository.flush();
    return ticket;
  }

  public void closeTicket(Long id){
    if (ticketRepository.existsById(id)) {

    }
  }

}
