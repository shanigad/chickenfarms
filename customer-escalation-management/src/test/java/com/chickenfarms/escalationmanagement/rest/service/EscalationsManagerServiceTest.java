package com.chickenfarms.escalationmanagement.rest.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.chickenfarms.escalationmanagement.exception.ResourceAlreadyExistException;
import com.chickenfarms.escalationmanagement.model.entity.Problem;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.model.payload.TicketCreationRequest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

class EscalationsManagerServiceTest {


//  @Test
//  public void SubmitTicketDuplicateTest(){
//    Long[] customers = {125L};
//    TicketCreationRequest
//        createdTicket = new TicketCreationRequest("provider", "description", 0L, "createdBy", customers);
//    when(problemService.getProblemIfExist(anyLong())).thenReturn(new Problem());
//    when(ticketRepository.findTicketByProblemAndProviderAndCreatedBy(ArgumentMatchers.any(Problem.class), anyString(), anyString()))
//        .thenReturn(new Ticket());
//    assertThatExceptionOfType(ResourceAlreadyExistException.class).isThrownBy(() ->{
//      ticketService.submitTicket(createdTicket);
//    });
//  }

}
