package com.chickenfarms.escalationmanagement.Util;

import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.model.payload.PostCommentRequest;
import com.chickenfarms.escalationmanagement.repository.TicketRepository;
import com.chickenfarms.escalationmanagement.rest.service.CommentService;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketUtils {

  private final TicketRepository ticketRepository;
  private final CommentService commentService;

  public Ticket saveToRepository(Ticket ticket) {
    ticket.setLastModifiedDate(new Date());
    ticket = ticketRepository.save(ticket);
    ticketRepository.flush();
    return ticket;
  }

  public void updateSla(Ticket ticket){
    ticket.slaTicking();
    if(ticket.getSla() < 0){
      ticket.setOomlette(true);
    }
    saveToRepository(ticket);
  }

  public void postSystemCommentToTicket(String comment, Ticket ticket){
    commentService.postComment(new PostCommentRequest(comment, "System"), ticket);
  }


//  public boolean isTicketOpen(Ticket ticket){
//    return ticket != null & !Status.isClosed(ticket.getStatus()) & !Status.isReconciled(ticket.getStatus());
//  }
}
