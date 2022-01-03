package com.chickenfarms.escalationmanagement.rest.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.chickenfarms.escalationmanagement.enums.Status;
import com.chickenfarms.escalationmanagement.exception.ChangeStatusException;
import com.chickenfarms.escalationmanagement.exception.ResourceNotFoundException;
import com.chickenfarms.escalationmanagement.model.entity.RootCause;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.repository.ProblemRepository;
import com.chickenfarms.escalationmanagement.repository.RootCauseRepository;
import com.chickenfarms.escalationmanagement.repository.TicketRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TicketManagerServiceTest {
 @Mock
 private TicketRepository ticketRepository;
 @Mock
 private CustomerService customerService;
 @Mock
 private ProblemRepository problemRepository;
 @Mock
 private RootCauseRepository rootCauseRepository;
 @InjectMocks
 private TicketManagerService ticketManagerService;


 @Test
 public void getTicketIfExistResourceNotFoundTest(){
   when(ticketRepository.existsById(anyLong())).thenReturn(false);
   assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() ->{
        ticketManagerService.getTicketIfExist(1L);
       });
 }

  @Test
  public void getTicketIfExistValidTest(){
   Ticket ticket = new Ticket();
   ticket.setId(1L);
    when(ticketRepository.existsById(anyLong())).thenReturn(true);
    when(ticketRepository.getById(anyLong())).thenReturn(ticket);
    assertThat(ticketManagerService.getTicketIfExist(1L)).isEqualTo(ticket);
  }


  @Test
  public void moveToReadyRootCauseNotFoundTest(){
   when(rootCauseRepository.findById(anyInt())).thenReturn(Optional.empty());
   assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() ->{
    ticketManagerService.moveTicketToReady(1L, 1);
   });
  }

 @Test
 public void moveToReadyTicketNotFoundTest(){
  when(rootCauseRepository.findById(anyInt())).thenReturn(Optional.of(new RootCause()));
  when(ticketRepository.findById(anyLong())).thenReturn(Optional.empty());
  assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() ->{
   ticketManagerService.moveTicketToReady(1L, 1);
  });
 }

 @Test
 public void moveToReadyClosedTicketTest(){
  Ticket ticket = new Ticket();
  ticket.setStatus(Status.CLOSED.getStatus());
  when(rootCauseRepository.findById(anyInt())).thenReturn(Optional.of(new RootCause()));
  when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));
  assertThatExceptionOfType(ChangeStatusException.class).isThrownBy(() ->{
   ticketManagerService.moveTicketToReady(1L, 1);
  });
 }

// @Test
// public void moveToReadyValidCreatedTicketTest(){
//  Ticket ticket = new Ticket();
//  ticket.setStatus(Status.CREATED.getStatus());
//  when(rootCauseRepository.findById(anyInt())).thenReturn(Optional.of(new RootCause()));
//  when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));
//  assertThatExceptionOfType(ChangeStatusException.class).isThrownBy(() ->{
//   ticketManagerService.moveTicketToReady(1L, 1);
//  });
// }

}
