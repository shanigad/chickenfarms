package com.chickenfarms.escalationmanagement.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.chickenfarms.escalationmanagement.model.payload.TicketCreationRequest;
import com.chickenfarms.escalationmanagement.model.payload.TicketFilterRequest;
import com.chickenfarms.escalationmanagement.rest.service.EscalationsManagerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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
  private EscalationsManagerService escalationsManagerService;


  @Test
  public void whenValidUrlAndMethodAndContentType_thenReturns200() throws Exception {

    mockMvc.perform(get("/escalation-management/tickets/filter/0?status=Created&tag=null&provider=null&problem=1&rootCause=null")
            .contentType("application/json"))
        .andExpect(status().isOk());
  }

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
