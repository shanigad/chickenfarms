package com.chickenfarms.escalationmanagement.rest.controller;

import com.chickenfarms.escalationmanagement.model.dto.CreatedTicketRequest;
import com.chickenfarms.escalationmanagement.rest.service.TicketManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {

  @Autowired
  private TicketManagerService ticketManagerService;

  @GetMapping("/ticket")
  public String getTicket(){
    return "Success";
  }

  @PostMapping("/ticket")
  public String createTicket(@RequestBody CreatedTicketRequest createdTicketRequest){
    return "Ticket successfully created with id: " + ticketManagerService.submitTicket(createdTicketRequest);
  }

  @PutMapping("/ticket")
  public String updateTicket(){
    return "Success";
  }
}
