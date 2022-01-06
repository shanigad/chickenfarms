package com.chickenfarms.escalationmanagement.rest.controller;

import com.chickenfarms.escalationmanagement.model.entity.Comment;
import com.chickenfarms.escalationmanagement.model.payload.CloseTicketRequest;
import com.chickenfarms.escalationmanagement.model.payload.PostCommentRequest;
import com.chickenfarms.escalationmanagement.model.payload.ResponsePayload;
import com.chickenfarms.escalationmanagement.model.payload.TicketComments;
import com.chickenfarms.escalationmanagement.model.payload.TicketResponse;
import com.chickenfarms.escalationmanagement.model.payload.TicketUpdateRequest;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.rest.service.CommentService;
import com.chickenfarms.escalationmanagement.rest.service.CustomerService;
import com.chickenfarms.escalationmanagement.rest.service.TicketService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket/{id}")
public class SingelTicketController {

  private final TicketService ticketService;
  private final CustomerService customerService;
  private final CommentService commentService;

  @GetMapping("")
  public TicketResponse getTicket(@PathVariable Long id){
    return new TicketResponse(ticketService.getTicketIfExist(id));
  }

  @GetMapping("/customers")
  public List<Long> getCustomersByTicketTicket(@PathVariable Long id){
    Ticket ticket = ticketService.getTicketIfExist(id);
    return customerService.getCustomersByTicket(ticket);
  }

  @GetMapping("/comments/{page}")
  public TicketComments getComments(@PathVariable Long id, @PathVariable int page){
    Ticket ticket = ticketService.getTicketIfExist(id);
    return commentService.getTicketComments(ticket,page);
  }

  @PostMapping("/split/{rootCauseId}")
  public ResponsePayload splitTicket(@PathVariable Long id, @PathVariable Long rootCauseId){
    Ticket ticket = ticketService.splitTicket(id, rootCauseId);
    return new ResponsePayload("Ticket number " + id + " successfully split to Ticket number " + ticket.getId(), new TicketResponse(ticket));
  }

  @PostMapping("/tag")
  public ResponsePayload addTagToTicket(@PathVariable Long id, @RequestParam String tagName){
    Ticket ticket = ticketService.addTagToTicket(id, tagName);
    return new ResponsePayload("Tag " + tagName + " successfully added to Ticket number" + id, ticket);
  }

  @PostMapping("/comment")
  public ResponsePayload postComment(@PathVariable Long id, @RequestBody
      PostCommentRequest postCommentRequest){
    Ticket ticket = ticketService.getTicketIfExist(id);
    Comment comment = commentService.postComment(postCommentRequest, ticket);
     return new ResponsePayload("Posted comment " + comment + " to Ticket number " + id, comment);
  }

  @PutMapping("")
  public ResponsePayload updateTicket(@PathVariable Long id
                             , @RequestBody TicketUpdateRequest ticketUpdateRequest){
    return new ResponsePayload("Ticket number "+ id +" updated successfully",
        new TicketResponse(ticketService.updateTicket(id, ticketUpdateRequest)));
  }

  @PutMapping("/ready/{rootCauseId}")
  public ResponsePayload updateTicketToReady(@PathVariable Long id, @PathVariable Long rootCauseId){
    return new ResponsePayload("Ticket updated to status 'Ready'",
        new TicketResponse(ticketService.moveTicketToReady(id, rootCauseId)));
  }

  @PutMapping("/close")
  public ResponsePayload closeTicket(@PathVariable Long id, @Valid @RequestBody CloseTicketRequest closeTicketRequest){
    return new ResponsePayload("Ticket updated to status 'Closed'",
        new TicketResponse(ticketService.closeTicket(id,closeTicketRequest))) ;
  }

}
