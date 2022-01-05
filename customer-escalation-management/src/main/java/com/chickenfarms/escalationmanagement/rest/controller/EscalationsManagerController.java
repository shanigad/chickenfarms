package com.chickenfarms.escalationmanagement.rest.controller;

import com.chickenfarms.escalationmanagement.model.dto.TicketCreationRequest;
import com.chickenfarms.escalationmanagement.model.dto.TicketResponse;
import com.chickenfarms.escalationmanagement.model.dto.TicketFilterRequest;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.rest.service.EscalationsManagerService;
import com.chickenfarms.escalationmanagement.rest.service.TicketService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/escalation-management")
public class EscalationsManagerController {

  private final TicketService ticketService;
  private final EscalationsManagerService escalationsManagerService;


//  @GetMapping("/tickets/tag")
//  public List<TicketResponse> getTicketsByTag(){
//    return null;
//  }
//
//  @GetMapping("/tickets/status")
//  public List<TicketResponse> getTicketsByStatus(){
//    return null;
//  }

  @GetMapping("/tickets/filter/{page}")
  public List<TicketResponse> getTicketsByFilter(@RequestBody TicketFilterRequest ticketFilterRequest,
                                                 @PathVariable int page){
    Page<Ticket> tickets = escalationsManagerService.getFilteredTickets(ticketFilterRequest, page);
    List<TicketResponse> dtoTickets = new ArrayList<>();
    tickets.stream().forEach(ticket -> dtoTickets.add(new TicketResponse(ticket)));
    return dtoTickets;
  }


  @PostMapping("/ticket")
  @ResponseStatus(HttpStatus.CREATED)
  public String createTicket(@Valid @RequestBody TicketCreationRequest ticketCreationRequest){
    return "Ticket successfully created with id: " + ticketService.submitTicket(
        ticketCreationRequest);
  }


}
