package com.chickenfarms.escalationmanagement.Util;

import com.chickenfarms.escalationmanagement.enums.Status;
import com.chickenfarms.escalationmanagement.model.entity.RootCause;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.model.payload.PostCommentRequest;
import com.chickenfarms.escalationmanagement.repository.TicketRepository;
import com.chickenfarms.escalationmanagement.rest.service.CommentService;
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
    // TODO combine customers
//    readyTicket.addCustomers(ticket.getCustomers());
    existingTicket.addTags(ticketToReconcile.getTags());
    existingTicket.setLastModifiedDate(new Date());
    ticketToReconcile.setStatus(Status.RECONCILED.getStatus());
    saveReconciledTickets(ticketToReconcile, existingTicket);
    return existingTicket;
  }

  @Transactional
  void saveReconciledTickets(Ticket ticket, Ticket readyTicket) {
    saveToRepository(readyTicket, "Ticket number" + ticket.getId() + " was reconciled to this Ticket");
    saveToRepository(ticket, "This Ticket was reconciled to Ticket number " + readyTicket.getId());
  }
}
