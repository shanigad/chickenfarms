package com.chickenfarms.escalationmanagement.rest.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.chickenfarms.escalationmanagement.exception.ResourceNotFoundException;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.repository.ProblemRepository;
import com.chickenfarms.escalationmanagement.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class TicketManagerServiceTest {
 private TicketRepository ticketRepository = Mockito.mock(TicketRepository.class);
 private CustomerService customerService = Mockito.mock(CustomerService.class);
 private ProblemRepository problemRepository = Mockito.mock(ProblemRepository.class);
 private TicketManagerService ticketManagerService;


 @BeforeEach
 public void initTicketManager(){
   ticketManagerService = new TicketManagerService(ticketRepository, customerService, problemRepository);
 }

 @Test
 public void getTicketIfExistResourceNotFoundTest(){
   when(ticketRepository.existsById(anyLong())).thenReturn(false);
   assertThrows(ResourceNotFoundException.class, ()->{ticketManagerService.getTicketIfExist(1L);});
 }

  @Test
  public void getTicketIfExistValidTest(){
   Ticket ticket = new Ticket();
   ticket.setId(1L);
    when(ticketRepository.existsById(anyLong())).thenReturn(true);
    when(ticketRepository.getById(anyLong())).thenReturn(ticket);
    assertTrue(ticketManagerService.getTicketIfExist(1L).equals(ticket));
  }

}
