package com.chickenfarms.escalationmanagement.rest.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.chickenfarms.escalationmanagement.exception.ResourceNotFoundException;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.repository.ProblemRepository;
import com.chickenfarms.escalationmanagement.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
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
 @InjectMocks
 private TicketManagerService ticketManagerService;


 @BeforeEach
 public void initTicketManager(){
 }

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

}
