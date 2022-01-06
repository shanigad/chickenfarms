package com.chickenfarms.escalationmanagement.model.payload;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketsResponse {
  private int page;
  private List<TicketResponse> tickets;
}
