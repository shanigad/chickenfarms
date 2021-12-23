package com.chickenfarms.escalationmanagement.rest.controller;

import com.chickenfarms.escalationmanagement.model.dto.TicketCreationRequest;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.rest.service.TicketManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;


@RestController
public class TicketController {

  @Autowired
  private TicketManagerService ticketManagerService;


  @GetMapping("/ticket/{id}")
  public Ticket getTicket(@PathVariable Long id){
    return ticketManagerService.getTicket(id);
  }

  @PostMapping("/ticket")
  @ResponseStatus(HttpStatus.CREATED)
  public String createTicket(@Valid @RequestBody TicketCreationRequest ticketCreationRequest){
    return "Ticket successfully created with id: " + ticketManagerService.submitTicket(
        ticketCreationRequest);
  }

  @PutMapping("/ticket")
  public String updateTicket(){
    return "Success";
  }

  @PutMapping("/ticket/{id}/ready")
  public String updateTicketToReady(@PathVariable Long id){
    return "Success";
  }

  @PutMapping("/ticket/{id}/close")
  public String closeTicket(@PathVariable Long id){
    ticketManagerService.closeTicket(id);
    return "Ticket " + id + "successfully closed" ;
  }
}
