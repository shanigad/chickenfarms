package com.chickenfarms.escalationmanagement.rest.service;

import com.chickenfarms.escalationmanagement.enums.Status;
import com.chickenfarms.escalationmanagement.exception.ResourceAlreadyExistException;
import com.chickenfarms.escalationmanagement.exception.ResourceNotFoundException;
import com.chickenfarms.escalationmanagement.model.dto.CloseTicketRequest;
import com.chickenfarms.escalationmanagement.model.dto.TicketCreationRequest;
import com.chickenfarms.escalationmanagement.model.entity.Problem;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.repository.ProblemRepository;
import com.chickenfarms.escalationmanagement.repository.TicketRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketManagerService {

  TicketRepository ticketRepository;

  CustomerService customerService;

  ProblemRepository problemRepository;

  @Autowired
  public TicketManagerService(
      TicketRepository ticketRepository,
      CustomerService customerService,
      ProblemRepository problemRepository) {
    this.ticketRepository = ticketRepository;
    this.customerService = customerService;
    this.problemRepository = problemRepository;
  }

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

  public Ticket getTicketIfExist(Long id){
    if(!ticketRepository.existsById(id)){
      throw new ResourceNotFoundException("Ticket", "id", String.valueOf(id));
    }
    return ticketRepository.getById(id);
  }


  private Ticket createTicket(TicketCreationRequest createdTicket, Problem problem) {
    Ticket ticket = new Ticket(createdTicket, problem);
    ticket = ticketRepository.save(ticket);
    ticketRepository.flush();
    return ticket;
  }

//  public void closeTicket(Long id, CloseTicketRequest closeTicketRequest){
//      Ticket ticket = getTicketIfExist(id);
//      if(Status.READY.getStatus().equals(ticket.getStatus())){
//        ticketRepository.setStatusAndIsResolvedForTicket(Status.CLOSED.getStatus(), closeTicketRequest.isResolved(), id);
//      }
//      else{
//        // Can't close ticket with status ?
//      }
//    }


//  public void moveTicketToReady(Long id){
//    Ticket ticket = getTicketIfExist(id);
//    if(Status.CREATED.getStatus().equals(ticket.getStatus())){
//      MoveToReady(id);
//    }
//    else{
//      // Can't change status from ? to Ready
//    }
//  }
//  @Transactional
//  void MoveToReady(Long id) {
//    ticketRepository.setStatusForTicket(Status.READY.getStatus(), id);
//  }


}
