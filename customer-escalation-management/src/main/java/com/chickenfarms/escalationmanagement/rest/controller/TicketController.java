package com.chickenfarms.escalationmanagement.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {

  @GetMapping("/ticket")
  public String getTicket(){
    return "Success";
  }

  @PostMapping("/ticket")
  public String createTicket(){
    return "Success";
  }

  @PutMapping("/ticket")
  public String updateTicket(){
    return "Success";
  }
}
