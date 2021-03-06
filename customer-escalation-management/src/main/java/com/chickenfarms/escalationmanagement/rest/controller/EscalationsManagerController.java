package com.chickenfarms.escalationmanagement.rest.controller;

import com.chickenfarms.escalationmanagement.model.payload.ResponsePayload;
import com.chickenfarms.escalationmanagement.model.payload.TicketCreationRequest;
import com.chickenfarms.escalationmanagement.model.payload.TicketResponse;
import com.chickenfarms.escalationmanagement.model.payload.TicketFilterRequest;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.model.payload.TicketsResponse;
import com.chickenfarms.escalationmanagement.rest.service.EscalationsManagerService;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/escalation-management")
public class EscalationsManagerController {

  private final EscalationsManagerService escalationsManagerService;

  @GetMapping("/tickets/next")
  public ResponsePayload getNextTicket(){
    Ticket ticket = escalationsManagerService.getNextTicket();
    if(ticket == null){
      return new ResponsePayload("No Ready tickets");
    }
    return new ResponsePayload("Kill them!", new TicketResponse(ticket));
  }

  @GetMapping("/tickets/filter/{page}")
  public TicketsResponse getTicketsByFilter(@PathVariable int page,
                                            @RequestParam String status,
                                            @RequestParam String tag,
                                            @RequestParam String provider,
                                            @RequestParam String problem,
                                            @RequestParam String rootCause){
    TicketFilterRequest ticketFilterRequest = new TicketFilterRequest(status, tag,provider,problem, rootCause);
    Page<Ticket> tickets = escalationsManagerService.getFilteredTickets(ticketFilterRequest, page);
    List<TicketResponse> ticketsResponse = new ArrayList<>();
    if(tickets != null){
      tickets.stream().forEach(ticket -> ticketsResponse.add(new TicketResponse(ticket)));
    }
    return new TicketsResponse(page, ticketsResponse);
  }


  @PostMapping("/ticket")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponsePayload createTicket(@Valid @RequestBody TicketCreationRequest ticketCreationRequest){
    Ticket ticket = escalationsManagerService.submitTicket(ticketCreationRequest);
    return new ResponsePayload("Ticket successfully created", new TicketResponse(ticket));
  }


}
