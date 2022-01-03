package com.chickenfarms.escalationmanagement.rest.service;

import com.chickenfarms.escalationmanagement.enums.Status;
import com.chickenfarms.escalationmanagement.exception.ChangeStatusException;
import com.chickenfarms.escalationmanagement.exception.ResourceAlreadyExistException;
import com.chickenfarms.escalationmanagement.exception.ResourceNotFoundException;
import com.chickenfarms.escalationmanagement.model.dto.TicketCreationRequest;
import com.chickenfarms.escalationmanagement.model.entity.Problem;
import com.chickenfarms.escalationmanagement.model.entity.RootCause;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.repository.ProblemRepository;
import com.chickenfarms.escalationmanagement.repository.RootCauseRepository;
import com.chickenfarms.escalationmanagement.repository.TicketRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketManagerService {

  private final TicketRepository ticketRepository;
  private final CustomerService customerService;
  private final ProblemRepository problemRepository;
  private final RootCauseRepository rootCauseRepository;

  public Long submitTicket(TicketCreationRequest createdTicket){
    Problem problem = problemRepository.findById(createdTicket.getProblem()).
        orElseThrow(()-> new ResourceNotFoundException("Problem", "id", String.valueOf(createdTicket.getProblem())));
    handleDuplicateTicket(createdTicket, problem);
    Long ticketId = saveTicketAndCustomers(createdTicket, problem);
    return ticketId;
  }

  @Transactional
  long saveTicketAndCustomers(TicketCreationRequest createdTicket, Problem problem) {
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
    return ticketRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Ticket", "id", String.valueOf(id)));
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


  public Long moveTicketToReady(Long ticketId, int rootCauseId){
    Ticket ticket = getTicketIfExist(ticketId);
    if(Status.CREATED.getStatus().equals(ticket.getStatus())){
      return getReadyTicket(ticket, rootCauseId);
    }
    else{
      throw new ChangeStatusException(Status.READY.getStatus(), "it is allowed only for tickets with status 'Created' and this ticket is  " + ticket.getStatus());
    }

  }
  Long getReadyTicket(Ticket ticket, int rootCauseId) {
    RootCause rootCause  = rootCauseRepository.findById(rootCauseId).
        orElseThrow(()-> new ResourceNotFoundException("RootCause", "id", String.valueOf(rootCauseId)));
    Optional<Ticket> readyTicket = ticketRepository.findTicketByProviderAndRootCause(ticket.getProvider(), rootCause);
    if(readyTicket.isPresent()){
      reconcileTickets(ticket, readyTicket.get());
      return readyTicket.get().getId();
    }
    return updateTicketStatusToReady(ticket, rootCause);
  }

  private void reconcileTickets(Ticket ticket, Ticket readyTicket) {
    // TODO combine customers
//    readyTicket.addCustomers(ticket.getCustomers());
    readyTicket.addTags(ticket.getTags());
    // TODO combine description?
    // TODO add comment for each ticket
    ticket.setStatus(Status.RECONCILED.getStatus());
    saveReconciledTickets(ticket, readyTicket);
  }

  @Transactional
   void saveReconciledTickets(Ticket ticket, Ticket readyTicket) {
    ticketRepository.save(readyTicket);
    ticketRepository.save(ticket);
  }

  private Long updateTicketStatusToReady(Ticket ticket, RootCause rootCause) {
    ticket.setStatus(Status.READY.getStatus());
    ticket.setRootCause(rootCause);
   return ticketRepository.save(ticket).getId();
  }


}
