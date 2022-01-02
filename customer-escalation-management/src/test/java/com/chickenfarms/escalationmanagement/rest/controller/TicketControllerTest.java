package com.chickenfarms.escalationmanagement.rest.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.chickenfarms.escalationmanagement.model.dto.TicketCreationRequest;
import com.chickenfarms.escalationmanagement.model.entity.Problem;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.rest.service.CustomerService;
import com.chickenfarms.escalationmanagement.rest.service.TicketManagerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




@WebMvcTest(controllers = TicketController.class)
public class TicketControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private TicketManagerService ticketManagerService;
  @MockBean
  private CustomerService customerService;
  @InjectMocks
  private TicketController ticketController;
//  private TicketCreationRequest createdTicket;

//  @BeforeAll
//  public void initParameters(){
//    Long[] customers = {125L};
//    createdTicket = new TicketCreationRequest("provider", "description", 101, "createdBy", customers);
//  }

  @Test
  public void whenValidUrlAndMethodAndContentTypeAndInput_thenReturns201() throws Exception {
    Long[] customers = {125L};
    TicketCreationRequest createdTicket = new TicketCreationRequest("provider", "description", 101, "createdBy", customers);
    mockMvc.perform(post("/ticket")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(createdTicket)))
        .andExpect(status().isCreated());
  }

  @Test
  void whenNullValue_thenReturns400() throws Exception {
   Long[] customers = {};
   TicketCreationRequest createdTicket = new TicketCreationRequest("", "", 101, "", customers);

    mockMvc.perform(post("/ticket")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(createdTicket)))
        .andExpect(status().isBadRequest());
  }

//  @Test
//  public void whenValidInput_thenReturnId() throws Exception {
//    Long[] customers = {125L};
//    TicketCreationRequest createdTicket = new TicketCreationRequest("provider", "description", 101, "createdBy", customers);
//    mockMvc.perform(post("/ticket")
//            .contentType("application/json")
//            .content(objectMapper.writeValueAsString(createdTicket)))
//        .andExpect(status().isCreated());
//
//
//    verify(ticketManagerService, times(1));
//  }
}
