package com.chickenfarms.escalationmanagement.model.dto;

import com.chickenfarms.escalationmanagement.model.entity.Problem;
import com.chickenfarms.escalationmanagement.model.entity.RootCause;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;

public class ReadyTicketDto extends TicketDto{
  private String rootCause;

  public ReadyTicketDto(Ticket ticket) {
    super(ticket);
    setRootCause(ticket.getRootCause());
  }

  public void setRootCause(RootCause rc) {
    this.rootCause = rc == null? "": rc.getName();
  }
}
