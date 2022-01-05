package com.chickenfarms.escalationmanagement.rest.service;

import com.chickenfarms.escalationmanagement.enums.Status;
import com.chickenfarms.escalationmanagement.exception.ChangeStatusException;
import com.chickenfarms.escalationmanagement.exception.ResourceAlreadyExistException;
import com.chickenfarms.escalationmanagement.exception.ResourceNotFoundException;
import com.chickenfarms.escalationmanagement.model.dto.CloseTicketRequest;
import com.chickenfarms.escalationmanagement.model.dto.TicketCreationRequest;
import com.chickenfarms.escalationmanagement.model.entity.Problem;
import com.chickenfarms.escalationmanagement.model.entity.RootCause;
import com.chickenfarms.escalationmanagement.model.entity.Tag;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.repository.ProblemRepository;
import com.chickenfarms.escalationmanagement.repository.RootCauseRepository;
import com.chickenfarms.escalationmanagement.repository.TicketRepository;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketManagerService {

  private final CustomerService customerService;
  private final TagService tagService;

  private final TicketRepository ticketRepository;
  private final ProblemRepository problemRepository;
  private final RootCauseRepository rootCauseRepository;

  public Long submitTicket(TicketCreationRequest createdTicket){
    Problem problem = problemRepository.findById(createdTicket.getProblem()).
        orElseThrow(()-> new ResourceNotFoundException("Problem", "id", String.valueOf(createdTicket.getProblem())));
    handleDuplicateTicket(createdTicket, problem);
    Long ticketId = saveTicketAndCustomers(createdTicket, problem);
    return ticketId;
  }

  public Ticket getTicketIfExist(Long id){
    return ticketRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Ticket", "id", String.valueOf(id)));
  }

  public void closeTicket(Long id, CloseTicketRequest closeTicketRequest){
    Ticket ticket = getTicketIfExist(id);
    if(Status.READY.getStatus().equals(ticket.getStatus())){
      ticket.setStatus(Status.CLOSED.getStatus());
      ticket.setClosedDate(new Date());
      saveToRepository(ticket);
//        ticketRepository.setStatusAndIsResolvedForTicket(Status.CLOSED.getStatus(), closeTicketRequest.isResolved(), id);
    }
    else{
      // Can't close ticket with status ?
    }
  }

  public Ticket addTagToTicket(Long ticketId, String tagName){
    Ticket ticket = getTicketIfExist(ticketId);
    Tag tag = tagService.getTagOrCreateIfMissing(tagName);
    ticket.addTag(tag);
    return saveToRepository(ticket);
  }

  public Long moveTicketToReady(Long ticketId, Long rootCauseId){
    Ticket ticket = getTicketIfExist(ticketId);
    if(Status.CREATED.getStatus().equals(ticket.getStatus())){
      return getReadyTicket(ticket, rootCauseId);
    }
    else{
      throw new ChangeStatusException(Status.READY.getStatus(), "it is allowed only for tickets with status 'Created' and this ticket is  " + ticket.getStatus());
    }
  }


  @Transactional
  void saveReconciledTickets(Ticket ticket, Ticket readyTicket) {
    saveToRepository(readyTicket);
    saveToRepository(ticket);
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
    Ticket ticket = ticketRepository.findTicketByProblemAndProviderAndCreatedBy(problem, createdTicket.getProvider(), createdTicket.getCreatedBy());
    if(ticket != null)
      throw new ResourceAlreadyExistException("Ticket",  ticket.getId());
  }


  private Ticket createTicket(TicketCreationRequest createdTicket, Problem problem) {
    Ticket ticket = new Ticket(createdTicket, problem);
    return saveToRepository(ticket);
  }

  private Ticket saveToRepository(Ticket ticket) {
    ticket.setLastModifiedDate(new Date());
    ticket = ticketRepository.save(ticket);
    ticketRepository.flush();
    return ticket;
  }


  private Long getReadyTicket(Ticket ticket, Long rootCauseId) {
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
    readyTicket.setLastModifiedDate(new Date());
    // TODO combine description?
    // TODO add comment for each ticket
    ticket.setStatus(Status.RECONCILED.getStatus());
    saveReconciledTickets(ticket, readyTicket);
  }

  private Long updateTicketStatusToReady(Ticket ticket, RootCause rootCause) {
    ticket.setStatus(Status.READY.getStatus());
    ticket.setRootCause(rootCause);
    return saveToRepository(ticket).getId();
  }




}
