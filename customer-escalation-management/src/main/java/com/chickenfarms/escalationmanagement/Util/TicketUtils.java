package com.chickenfarms.escalationmanagement.Util;

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

  public void updateSla(Ticket ticket){
    ticket.slaTicking();
    if(ticket.getSla() < 0){
      ticket.setOomlette(true);
    }
    saveToRepository(ticket);
  }


//  public boolean isTicketOpen(Ticket ticket){
//    return ticket != null & !Status.isClosed(ticket.getStatus()) & !Status.isReconciled(ticket.getStatus());
//  }
}
