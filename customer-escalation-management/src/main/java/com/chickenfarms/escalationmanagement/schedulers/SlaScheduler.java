package com.chickenfarms.escalationmanagement.schedulers;

import com.chickenfarms.escalationmanagement.enums.Status;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.repository.TicketRepository;
import com.chickenfarms.escalationmanagement.Util.TicketUtils;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@EnableScheduling
@AllArgsConstructor
@Service
public class SlaScheduler {

  private final TicketRepository ticketRepository;
  private final TicketUtils ticketUtils;

  @Scheduled(cron = "@hourly")
  public void updateHourlySla(){
    Date date = new Date();
    List<Ticket> hourlyTickets = ticketRepository.findAllBySlaHourAndStatus(date.getHours(), Status.READY.getStatus());
    hourlyTickets.forEach(ticket -> ticketUtils.updateSla(ticket));
  }
}
