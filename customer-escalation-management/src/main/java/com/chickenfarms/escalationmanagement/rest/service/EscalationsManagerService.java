package com.chickenfarms.escalationmanagement.rest.service;

import com.chickenfarms.escalationmanagement.enums.Status;
import com.chickenfarms.escalationmanagement.exception.ResourceAlreadyExistException;
import com.chickenfarms.escalationmanagement.model.payload.TicketCreationRequest;
import com.chickenfarms.escalationmanagement.model.payload.TicketFilterRequest;
import com.chickenfarms.escalationmanagement.model.entity.Problem;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EscalationsManagerService {

  private final TicketRepository ticketRepository;
  private final TicketUtils ticketUtils;
  private final ProblemService problemService;
  private final CustomerService customerService;


  public Ticket submitTicket(TicketCreationRequest createdTicket){
    Problem problem = problemService.getProblemIfExist(createdTicket.getProblem());
    handleDuplicateTicket(createdTicket, problem);
    return saveTicketAndCustomers(createdTicket, problem);
  }

  public Page<Ticket> getFilteredTickets(TicketFilterRequest ticketFilterRequest, int pageNum){
    String status = getStatusOrNull(ticketFilterRequest);
    Problem problem = getProblemOrNull(ticketFilterRequest);
    Page<Ticket> filteredTickets = ticketRepository.getAllByStatusAndProviderAndProblem(status, ticketFilterRequest.getProvider(), problem, PageRequest.of(pageNum, 5));
    return filteredTickets;
  }

  private Problem getProblemOrNull(TicketFilterRequest ticketFilterRequest) {
    return ticketFilterRequest.getProblem() != null ?
        problemService.getProblemIfExist(ticketFilterRequest.getProblem()) : null;
  }

  //TODO  Move to TicketUtils
  private void handleDuplicateTicket(TicketCreationRequest createdTicket, Problem problem) {
    //TODO - Add customers to validation
    Ticket ticket = ticketRepository.findTicketByProblemAndProviderAndCreatedBy(problem, createdTicket.getProvider(), createdTicket.getCreatedBy());
    if(ticket != null)
      throw new ResourceAlreadyExistException("Ticket",  ticket.getId());
  }

  private String getStatusOrNull(TicketFilterRequest ticketFilterRequest) {
    return ticketFilterRequest.getStatus() != null ? ticketFilterRequest.getStatus().getStatus() : null;
  }

  @Transactional
  Ticket saveTicketAndCustomers(TicketCreationRequest createdTicket, Problem problem) {
    Ticket ticket = createTicket(createdTicket, problem);
    customerService.attachCustomersToTicket(createdTicket.getCustomers(), ticket, ticket.getCreationDate());
    return ticket;
  }

  private Ticket createTicket(TicketCreationRequest createdTicket, Problem problem) {
    Ticket ticket = new Ticket(createdTicket, problem);
    return ticketUtils.saveToRepository(ticket);
  }

}
