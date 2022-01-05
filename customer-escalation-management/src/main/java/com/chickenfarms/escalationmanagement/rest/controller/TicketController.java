package com.chickenfarms.escalationmanagement.rest.controller;

import com.chickenfarms.escalationmanagement.model.dto.CloseTicketRequest;
import com.chickenfarms.escalationmanagement.model.dto.TicketCreationRequest;
import com.chickenfarms.escalationmanagement.model.dto.TicketDto;
import com.chickenfarms.escalationmanagement.model.dto.TicketUpdateRequest;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping("/ticket/{id}/customers")
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

  @PostMapping("/ticket/{id}/split/{rootCauseId}")
  public String splitTicket(@PathVariable Long id, @PathVariable Long rootCauseId){
    // is rootCause exist
    // is Ticket exist
    // is ticket is ready/created/reconciled
    // create ticket and move customers from old one
    // move new ticket to ready reconcile
    // set SLA to earliest Customer’s SLA from the original Ticket
    return "Ticket id x successfully splited to Ticket id y";
  }

  @PostMapping("/ticket/{id}/tag")
  public String addTagToTicket(@PathVariable Long id, @RequestParam String tagName){
    Ticket t = ticketManagerService.addTagToTicket(id, tagName);
    return "Tag " + tagName + " successfully added to Ticket " + id + " ticket = " + t ;
  }

  @PutMapping("/ticket/{id}")
  public String updateTicket(@PathVariable Long id
                             ,@RequestBody TicketUpdateRequest ticketUpdateRequest){
    // Check if  ticket id exist
    // Check what fields we want to update
    // if we want to change root cause - only for ready ticket
    // check reconciliation
    // set new last modified date
    return "Success";
  }


  @PutMapping("/ticket/{ticketId}/ready/{rootCauseId}")
  public Long updateTicketToReady(@PathVariable Long ticketId, @PathVariable Long rootCauseId){
    return ticketManagerService.moveTicketToReady(ticketId, rootCauseId);
  }

  @PutMapping("/ticket/{id}/close")
  public String closeTicket(@PathVariable Long id, @Valid @RequestBody CloseTicketRequest payload){
    //close only ready ticket
    // check is resolve
    // update closed date
    return "Ticket " + id + "successfully closed" ;
  }

}
