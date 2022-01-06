package com.chickenfarms.escalationmanagement.rest.service;

import com.chickenfarms.escalationmanagement.Util.TicketUtils;
import com.chickenfarms.escalationmanagement.enums.Status;
import com.chickenfarms.escalationmanagement.exception.ChangeStatusException;
import com.chickenfarms.escalationmanagement.exception.ResourceNotFoundException;
import com.chickenfarms.escalationmanagement.model.payload.CloseTicketRequest;
import com.chickenfarms.escalationmanagement.model.payload.TicketUpdateRequest;
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

  private final TicketUtils ticketUtils;
  private final TicketRepository ticketRepository;
  private final TagService tagService;
  private final ProblemService problemService;
  private final RootCauseService rootCauseService;


  public Ticket getTicketIfExist(Long id){
    return ticketRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Ticket", "id", String.valueOf(id)));
  }

  public Ticket closeTicket(Long id, CloseTicketRequest closeTicketRequest){
    Ticket ticket = getTicketIfExist(id);
    if(Status.READY.getStatus().equals(ticket.getStatus())){
      ticket.setStatus(Status.CLOSED.getStatus());
      ticket.setResolved(closeTicketRequest.isResolved());
      ticket.setClosedDate(new Date());
      return ticketUtils.saveToRepository(ticket);
    }
    else{
      throw new ChangeStatusException(Status.CLOSED.getStatus(), "You tried to change status from status " + ticket.getStatus() + " ,it is allowed only for tickets with status 'Ready'");
    }
  }

  public Ticket addTagToTicket(Long ticketId, String tagName){
    Ticket ticket = getTicketIfExist(ticketId);
    Tag tag = tagService.getTagOrCreateIfMissing(tagName);
    ticket.addTag(tag);
    return ticketUtils.saveToRepository(ticket);
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
    ticket = ticketUtils.saveToRepository(ticket);
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
    ticketUtils.saveToRepository(splitTicket);
    // set SLA to earliest Customer’s SLA from the original Ticket
    splitTicket = moveTicketToReady(splitTicket.getId(), rootCause.getId());
    return splitTicket;
  }

    @Transactional
  void saveReconciledTickets(Ticket ticket, Ticket readyTicket) {
      ticketUtils.saveToRepository(readyTicket);
      ticketUtils.saveToRepository(ticket);
  }

  private Ticket getReadyTicket(Ticket ticket, Long rootCauseId) {
    RootCause rootCause = rootCauseService.getRootCauseIfExist(rootCauseId);
    Optional<Ticket> readyTicket = ticketRepository.findTicketByProviderAndRootCause(ticket.getProvider(), rootCause);
    //TODO  Move to TicketUtils
    if(readyTicket.isPresent() && Status.isReady(readyTicket.get().getStatus())){
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
    ticket.setSla(3);
//    ticket.setReadyDate(new Date());
    // TODO change date
    ticket.setSlaHour(new Date().getHours());
    return ticketUtils.saveToRepository(ticket);
  }




}
