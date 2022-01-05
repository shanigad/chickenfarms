package com.chickenfarms.escalationmanagement.rest.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.chickenfarms.escalationmanagement.model.dto.TicketCreationRequest;
import com.chickenfarms.escalationmanagement.rest.service.CustomerService;
import com.chickenfarms.escalationmanagement.rest.service.EscalationsManagerService;
import com.chickenfarms.escalationmanagement.rest.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = EscalationsManagerController.class)
class EscalationsManagerControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private TicketService ticketService;
  @MockBean
  private  EscalationsManagerService escalationsManagerService;
  @MockBean
  private CustomerService customerService;
  @InjectMocks
  private EscalationsManagerController escalationsManagerController;

  @Test
  public void whenValidUrlAndMethodAndContentTypeAndInput_thenReturns201() throws Exception {
    Long[] customers = {125L};
    TicketCreationRequest
        createdTicket = new TicketCreationRequest("provider", "description", 101L, "createdBy", customers);
    mockMvc.perform(post("/escalation-management/ticket")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(createdTicket)))
        .andExpect(status().isCreated());
  }

  @Test
  void whenEmptyValues_thenReturns400() throws Exception {
    Long[] customers = {};
    TicketCreationRequest createdTicket = new TicketCreationRequest("", "", 101L, "", customers);

    mockMvc.perform(post("/escalation-management/ticket")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(createdTicket)))
        .andExpect(status().isBadRequest());
  }
}
