package com.chickenfarms.escalationmanagement.rest.controller;

import com.chickenfarms.escalationmanagement.model.dto.TicketCreationRequest;
import com.chickenfarms.escalationmanagement.model.dto.TicketDto;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.rest.service.CustomerService;
import com.chickenfarms.escalationmanagement.rest.service.TicketManagerService;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class TicketController {

  private final TicketManagerService ticketManagerService;
  private final CustomerService customerService;

  @GetMapping("/ticket/{id}")
  public TicketDto getTicket(@PathVariable Long id){
    return new TicketDto(ticketManagerService.getTicketIfExist(id));
  }

  @GetMapping("/ticket/customers/{id}")
  public ArrayList<Long> getCustomersByTicketTicket(@PathVariable Long id){
    Ticket ticket = ticketManagerService.getTicketIfExist(id);
    return customerService.getCustomersByTicket(ticket);
  }

  @PostMapping("/ticket")
  @ResponseStatus(HttpStatus.CREATED)
  public String createTicket(@Valid @RequestBody TicketCreationRequest ticketCreationRequest){
    return "Ticket successfully created with id: " + ticketManagerService.submitTicket(
        ticketCreationRequest);
  }

//  @PutMapping("/ticket")
//  public String updateTicket(){
//    return "Success";
//  }
//
//  @PutMapping("/ticket/{id}/ready")
//  public String updateTicketToReady(@PathVariable Long id){
//    ticketManagerService.moveTicketToReady(id);
//    return "Success";
//  }
//
//  @PutMapping("/ticket/{id}/close")
//  public String closeTicket(@PathVariable Long id, @Valid @RequestBody CloseTicketRequest payload){
//    ticketManagerService.closeTicket(id, payload);
//    return "Ticket " + id + "successfully closed" ;
//  }
}
