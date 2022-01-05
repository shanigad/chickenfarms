package com.chickenfarms.escalationmanagement.rest.controller;

import com.chickenfarms.escalationmanagement.model.dto.TicketCreationRequest;
import com.chickenfarms.escalationmanagement.rest.service.CustomerService;
import com.chickenfarms.escalationmanagement.rest.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




@WebMvcTest(controllers = SingelTicketController.class)
public class SingelTicketControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private TicketService ticketService;
  @MockBean
  private CustomerService customerService;
  @InjectMocks
  private SingelTicketController singelTicketController;


//  @Test
//  public void whenValidUrlAndMethodAndContentTypeAndInput_thenReturns201() throws Exception {
//    Long[] customers = {125L};
//    TicketCreationRequest createdTicket = new TicketCreationRequest("provider", "description", 101L, "createdBy", customers);
//    mockMvc.perform(post("/ticket")
//            .contentType("application/json")
//            .content(objectMapper.writeValueAsString(createdTicket)))
//        .andExpect(status().isCreated());
//  }
//
//  @Test
//  void whenEmptyValues_thenReturns400() throws Exception {
//   Long[] customers = {};
//   TicketCreationRequest createdTicket = new TicketCreationRequest("", "", 101L, "", customers);
//
//    mockMvc.perform(post("/ticket")
//            .contentType("application/json")
//            .content(objectMapper.writeValueAsString(createdTicket)))
//        .andExpect(status().isBadRequest());
//  }

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
