package com.chickenfarms.escalationmanagement.model.dto;

import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import java.util.Date;

public class ClosedTicketDto extends ReadyTicketDto{
  private boolean isResolved;
  private Date closedDate;

  public ClosedTicketDto(Ticket ticket) {
    super(ticket);
    this.isResolved = ticket.isResolved();
    this.closedDate = ticket.getClosedDate();
  }
}
