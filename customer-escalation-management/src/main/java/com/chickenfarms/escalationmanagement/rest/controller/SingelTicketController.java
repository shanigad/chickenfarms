package com.chickenfarms.escalationmanagement.rest.controller;

import com.chickenfarms.escalationmanagement.model.dto.CloseTicketRequest;
import com.chickenfarms.escalationmanagement.model.dto.PostCommentRequest;
import com.chickenfarms.escalationmanagement.model.dto.TicketResponse;
import com.chickenfarms.escalationmanagement.model.dto.TicketUpdateRequest;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.rest.service.CommentService;
import com.chickenfarms.escalationmanagement.rest.service.CustomerService;
import com.chickenfarms.escalationmanagement.rest.service.TicketService;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class SingelTicketController {

  private final TicketService ticketService;
  private final CustomerService customerService;
  private final CommentService commentService;

  @GetMapping("/ticket/{id}")
  public TicketResponse getTicket(@PathVariable Long id){
    return new TicketResponse(ticketService.getTicketIfExist(id));
  }

  @GetMapping("/ticket/{id}/customers")
  public ArrayList<Long> getCustomersByTicketTicket(@PathVariable Long id){
    Ticket ticket = ticketService.getTicketIfExist(id);
    return customerService.getCustomersByTicket(ticket);
  }

  @GetMapping("/ticket/{id}/comments/{page}")
  public List<String> getComments(@PathVariable Long id, @PathVariable int page){
    Ticket ticket = ticketService.getTicketIfExist(id);
    List<String> comments = new ArrayList<>();
    commentService.getTicketComments(ticket,page).forEach(comment -> comments.add(comment.toString()));
    return comments;
  }

  @PostMapping("/ticket/{id}/split/{rootCauseId}")
  public TicketResponse splitTicket(@PathVariable Long id, @PathVariable Long rootCauseId){
    Ticket ticket = ticketService.splitTicket(id, rootCauseId);
    return new TicketResponse(ticket);
//    return "Ticket id x successfully split to Ticket id y";
  }

  @PostMapping("/ticket/{id}/tag")
  public String addTagToTicket(@PathVariable Long id, @RequestParam String tagName){
    Ticket t = ticketService.addTagToTicket(id, tagName);
    return "Tag " + tagName + " successfully added to Ticket " + id;
  }

  @PostMapping("/ticket/{id}/comment")
  public String postComment(@PathVariable Long id, @RequestBody
      PostCommentRequest postCommentRequest){
    Ticket ticket = ticketService.getTicketIfExist(id);
     return commentService.postComment(postCommentRequest, ticket).toString();
  }

  @PutMapping("/ticket/{id}")
  public TicketResponse updateTicket(@PathVariable Long id
                             , @RequestBody TicketUpdateRequest ticketUpdateRequest){
    return new TicketResponse(ticketService.updateTicket(id, ticketUpdateRequest));
  }

  @PutMapping("/ticket/{ticketId}/ready/{rootCauseId}")
  public TicketResponse updateTicketToReady(@PathVariable Long ticketId, @PathVariable Long rootCauseId){

    return new TicketResponse(ticketService.moveTicketToReady(ticketId, rootCauseId));
  }

  @PutMapping("/ticket/{id}/close")
  public String closeTicket(@PathVariable Long id, @Valid @RequestBody CloseTicketRequest closeTicketRequest){
    ticketService.closeTicket(id,closeTicketRequest);
    return "Ticket " + id + " successfully closed" ;
  }

}
