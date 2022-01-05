package com.chickenfarms.escalationmanagement.rest.service;

import com.chickenfarms.escalationmanagement.enums.Status;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.repository.TicketRepository;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketUtils {

  private final TicketRepository ticketRepository;

  public Ticket saveToRepository(Ticket ticket) {
    ticket.setLastModifiedDate(new Date());
    ticket = ticketRepository.save(ticket);
    ticketRepository.flush();
    return ticket;
  }

//  public boolean isTicketOpen(Ticket ticket){
//    return ticket != null & !Status.isClosed(ticket.getStatus()) & !Status.isReconciled(ticket.getStatus());
//  }
}
