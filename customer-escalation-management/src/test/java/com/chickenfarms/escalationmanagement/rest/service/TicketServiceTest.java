package com.chickenfarms.escalationmanagement.rest.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.chickenfarms.escalationmanagement.enums.Status;
import com.chickenfarms.escalationmanagement.exception.ChangeStatusException;
import com.chickenfarms.escalationmanagement.exception.ResourceAlreadyExistException;
import com.chickenfarms.escalationmanagement.exception.ResourceNotFoundException;
import com.chickenfarms.escalationmanagement.model.dto.TicketCreationRequest;
import com.chickenfarms.escalationmanagement.model.entity.Problem;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.repository.ProblemRepository;
import com.chickenfarms.escalationmanagement.repository.RootCauseRepository;
import com.chickenfarms.escalationmanagement.repository.TicketRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
  @Mock
  private TicketRepository ticketRepository;
  @Mock
  private CustomerService customerService;
  @Mock
  private ProblemRepository problemRepository;
  @Mock
  private RootCauseRepository rootCauseRepository;
  @InjectMocks
  private TicketService ticketService;




  @Test
  public void getTicketIfExistResourceNotFoundTest(){
    when(ticketRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() ->{
      ticketService.getTicketIfExist(1L);
    });
  }

  @Test
  public void getTicketIfExistValidTest(){
    Ticket ticket = new Ticket();
    ticket.setId(1L);
    when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));
    assertThat(ticketService.getTicketIfExist(1L)).isEqualTo(ticket);
  }


  @Test
  public void SubmitTicketProblemNotFoundTest(){
    Long[] customers = {125L};
    TicketCreationRequest createdTicket = new TicketCreationRequest("provider", "description", 0L, "createdBy", customers);
    when(problemRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() ->{
      ticketService.submitTicket(createdTicket);
    });
  }

  @Test
  public void SubmitTicketDuplicateTest(){
    Long[] customers = {125L};
    TicketCreationRequest createdTicket = new TicketCreationRequest("provider", "description", 0L, "createdBy", customers);
    when(problemRepository.findById(anyLong())).thenReturn(Optional.of(new Problem()));
    when(ticketRepository.findTicketByProblemAndProviderAndCreatedBy(ArgumentMatchers.any(Problem.class), anyString(), anyString()))
        .thenReturn(new Ticket());
    assertThatExceptionOfType(ResourceAlreadyExistException.class).isThrownBy(() ->{
      ticketService.submitTicket(createdTicket);
    });
  }

  @Test
  public void moveToReadyRootCauseNotFoundTest(){
    assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() ->{
      ticketService.moveTicketToReady(1L, 1L);
    });
  }

  @Test
  public void moveToReadyTicketNotFoundTest(){
    when(ticketRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() ->{
      ticketService.moveTicketToReady(1L, 1L);
    });
  }

  @Test
  public void moveToReadyClosedTicketTest(){
    Ticket ticket = new Ticket();
    ticket.setStatus(Status.CLOSED.getStatus());
    when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));
    assertThatExceptionOfType(ChangeStatusException.class).isThrownBy(() ->{
      ticketService.moveTicketToReady(1L, 1L);
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
