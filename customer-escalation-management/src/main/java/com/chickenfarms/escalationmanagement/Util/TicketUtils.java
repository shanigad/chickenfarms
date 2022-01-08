package com.chickenfarms.escalationmanagement.Util;

import com.chickenfarms.escalationmanagement.enums.Status;
import com.chickenfarms.escalationmanagement.model.entity.RootCause;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.model.payload.PostCommentRequest;
import com.chickenfarms.escalationmanagement.repository.TicketRepository;
import com.chickenfarms.escalationmanagement.rest.service.CommentService;
import com.chickenfarms.escalationmanagement.rest.service.CustomerService;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketUtils {

  private final TicketRepository ticketRepository;
  private final CommentService commentService;
  private final CustomerService customerService;

  public Ticket saveToRepository(Ticket ticket, String comment) {
    ticket.setLastModifiedDate(new Date());
    ticket = ticketRepository.save(ticket);
    ticketRepository.flush();
    postSystemCommentToTicket(comment, ticket);
    return ticket;
  }

  public void updateSla(Ticket ticket){
    ticket.slaTicking();
    if(ticket.getSla() < 0){
      ticket.setOomlette(true);
    }
    saveToRepository(ticket, "SLA was updated to " + ticket.getSla());
  }

  public void postSystemCommentToTicket(String comment, Ticket ticket){
    commentService.postComment(new PostCommentRequest(comment, "System"), ticket);
  }

  public Ticket reconcileTickets(Ticket ticketToReconcile, Ticket existingTicket) {
    ArrayList<Long> customers = customerService.getCustomersByTicket(ticketToReconcile);
    customers.stream().forEach(customer -> addCustomerToTicket(existingTicket, customer));
    existingTicket.addTags(ticketToReconcile.getTags());
    existingTicket.setLastModifiedDate(new Date());
    ticketToReconcile.setStatus(Status.RECONCILED.getStatus());
    ticketToReconcile.setRootCause(existingTicket.getRootCause());
    return saveReconciledTickets(ticketToReconcile, existingTicket);
  }

  public Ticket addCustomerToTicket(Ticket ticket, Long customerId){
    customerService.attachCustomerToTicket(customerId, ticket, null);
    ticket.increaseImpact();
    ticket = saveToRepository(ticket, "Customer " + customerId + " was added to Ticket");
    return ticket;
  }

  @Transactional
  Ticket saveReconciledTickets(Ticket ticket, Ticket readyTicket) {
    saveToRepository(readyTicket, "Ticket number" + ticket.getId() + " was reconciled to this Ticket");
    return saveToRepository(ticket, "This Ticket was reconciled to Ticket number " + readyTicket.getId());
  }
}
