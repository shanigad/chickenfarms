package com.chickenfarms.escalationmanagement.rest.service;

import com.chickenfarms.escalationmanagement.enums.Status;
import com.chickenfarms.escalationmanagement.exception.ChangeStatusException;
import com.chickenfarms.escalationmanagement.exception.ResourceAlreadyExistException;
import com.chickenfarms.escalationmanagement.exception.ResourceNotFoundException;
import com.chickenfarms.escalationmanagement.model.dto.CloseTicketRequest;
import com.chickenfarms.escalationmanagement.model.dto.TicketCreationRequest;
import com.chickenfarms.escalationmanagement.model.dto.TicketUpdateRequest;
import com.chickenfarms.escalationmanagement.model.entity.Problem;
import com.chickenfarms.escalationmanagement.model.entity.RootCause;
import com.chickenfarms.escalationmanagement.model.entity.Tag;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.repository.TicketRepository;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketService {

  private final CustomerService customerService;
  private final TagService tagService;
  private final ProblemService problemService;
  private final RootCauseService rootCauseService;

  private final TicketRepository ticketRepository;

  public Long submitTicket(TicketCreationRequest createdTicket){
    Problem problem = problemService.getProblemIfExist(createdTicket.getProblem());
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
      ticket.setResolved(closeTicketRequest.isResolved());
      ticket.setClosedDate(new Date());
      saveToRepository(ticket);
    }
    else{
      throw new ChangeStatusException(Status.CLOSED.getStatus(), "You tried to change status from status " + ticket.getStatus() + " ,it is allowed only for tickets with status 'Ready'");
    }
  }

  public Ticket addTagToTicket(Long ticketId, String tagName){
    Ticket ticket = getTicketIfExist(ticketId);
    Tag tag = tagService.getTagOrCreateIfMissing(tagName);
    ticket.addTag(tag);
    return saveToRepository(ticket);
  }

  public Ticket moveTicketToReady(Long ticketId, Long rootCauseId){
    Ticket ticket = getTicketIfExist(ticketId);
    if(Status.CREATED.getStatus().equals(ticket.getStatus())){
      return getReadyTicket(ticket, rootCauseId);
    }
    else{
      throw new ChangeStatusException(Status.READY.getStatus(), "You tried to change status from status " + ticket.getStatus() + " ,it is allowed only for tickets with status 'Created'");
    }
  }

  public Ticket updateTicket(Long id, TicketUpdateRequest updateRequest){
    Ticket ticket = getTicketIfExist(id);
    if(!updateRequest.getDescription().isEmpty()){
      ticket.setDescription(updateRequest.getDescription());
    }
    if(updateRequest.getProblem() != null){
      Problem problem = problemService.getProblemIfExist(updateRequest.getProblem());
      ticket.setProblem(problem);
    }
    ticket = saveToRepository(ticket);
    return ticket;
  }

  public Ticket splitTicket(Long id, Long RootCauseId){
    Ticket ticket = getTicketIfExist(id);
    //check not the same root cause
    RootCause rootCause = rootCauseService.getRootCauseIfExist(RootCauseId);
    if(Status.CLOSED.getStatus().equals(ticket.getStatus())){
      throw new ChangeStatusException(Status.READY.getStatus(), "You tried to split " + ticket.getStatus() + " ticket,  it is allowed only for tickets with status 'Created'/'Ready'/'Reconciled'");
    }
    Ticket splitTicket = new Ticket(ticket);
    // create ticket and move customers from old one
    saveToRepository(splitTicket);
    splitTicket = moveTicketToReady(splitTicket.getId(), rootCause.getId());
    // set SLA to earliest Customer’s SLA from the original Ticket
    return splitTicket;
  }


    @Transactional
  void saveReconciledTickets(Ticket ticket, Ticket readyTicket) {
    saveToRepository(readyTicket);
    saveToRepository(ticket);
  }

  @Transactional
  long saveTicketAndCustomers(TicketCreationRequest createdTicket, Problem problem) {
    Ticket ticket = createTicket(createdTicket, problem);
    customerService.attachCustomersToTicket(createdTicket.getCustomers(), ticket, ticket.getCreationDate());
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

  private Ticket getReadyTicket(Ticket ticket, Long rootCauseId) {
    RootCause rootCause = rootCauseService.getRootCauseIfExist(rootCauseId);
    Optional<Ticket> readyTicket = ticketRepository.findTicketByProviderAndRootCause(ticket.getProvider(), rootCause);
    if(readyTicket.isPresent()){
      reconcileTickets(ticket, readyTicket.get());
      return readyTicket.get();
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

  private Ticket updateTicketStatusToReady(Ticket ticket, RootCause rootCause) {
    ticket.setStatus(Status.READY.getStatus());
    ticket.setRootCause(rootCause);
    return saveToRepository(ticket);
  }




}
