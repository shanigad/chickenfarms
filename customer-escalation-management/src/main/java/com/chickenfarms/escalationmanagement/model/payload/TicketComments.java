package com.chickenfarms.escalationmanagement.model.payload;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketComments {
  private Long ticketNumber;
  private int page;
  private List<String> ticketComments;
}
