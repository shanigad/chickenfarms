package com.chickenfarms.escalationmanagement.rest.service;

import com.chickenfarms.escalationmanagement.exception.ResourceAlreadyExistException;
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
    handleDuplicateTicket(createdTicket, problem);
    Ticket ticket = createTicket(createdTicket, problem);
    customerService.attachCustomersToTicket(createdTicket.getCustomers(), ticket, ticket.getCreatedDate());
    return ticket.getId();
  }

  private void handleDuplicateTicket(TicketCreationRequest createdTicket, Problem problem) {
    //TODO - Add customers to validation
    //TODO - Does this should be handled as exception
    Ticket t = ticketRepository.findTicketByProblemAndProviderAndCreatedBy(problem, createdTicket.getProvider(), createdTicket.getCreatedBy());
    if(t != null)
      throw new ResourceAlreadyExistException("Ticket",  String.valueOf(t.getId()));
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
