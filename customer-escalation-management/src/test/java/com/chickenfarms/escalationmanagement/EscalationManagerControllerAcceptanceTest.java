package com.chickenfarms.escalationmanagement;

import com.chickenfarms.escalationmanagement.model.payload.ResponsePayload;
import com.chickenfarms.escalationmanagement.model.payload.TicketCreationRequest;
import com.chickenfarms.escalationmanagement.model.payload.TicketResponse;
import com.chickenfarms.escalationmanagement.model.payload.TicketsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EscalationManagerControllerAcceptanceTest {

  @LocalServerPort
  int randomServerPort;

  private RestTemplate restTemplate;
  private String url;

  @BeforeEach
  void setUp(){
    restTemplate = new RestTemplate();
    url = "http://localhost:" + randomServerPort + "/escalation-management";
  }

  @Test
  void shouldCreateNewTicket() throws Exception {
    Long[] customers = {123123L};
    TicketCreationRequest ticketCreationRequest = new TicketCreationRequest("Meshek kuku", "My chickens data is null", 102L, "Angry customer", customers);
    ResponseEntity<ResponsePayload>
        responseEntity=restTemplate.postForEntity(url+"/ticket",ticketCreationRequest, ResponsePayload.class);
    assertEquals(CREATED, responseEntity.getStatusCode());
    assertEquals("Ticket successfully created", responseEntity.getBody().getMessage());
    ObjectMapper mapper = new ObjectMapper();
    TicketResponse ticketResponse = mapper.convertValue(responseEntity.getBody().getContext().get(0), TicketResponse.class);
    assertEquals("Meshek kuku", ticketResponse.getProvider());
    assertEquals("My chickens data is null", ticketResponse.getDescription());
    assertEquals("Missing Chicken", ticketResponse.getProblem());
    assertEquals("Angry customer", ticketResponse.getCreatedBy());
    assertEquals("Created", ticketResponse.getStatus());
    assertEquals(null, ticketResponse.getRootCause());
  }


  @Test
  void shouldGetFilteredTickets() throws Exception {
    ResponseEntity<TicketsResponse>
        responseEntity=restTemplate.getForEntity(url+"/tickets/filter/0?status=Created&tag=null&provider=null&problem=1&rootCause=null" ,TicketsResponse.class);
        assertEquals(OK, responseEntity.getStatusCode());
        assertEquals("0", responseEntity.getBody().getPage());
//        assertEquals("0", responseEntity.getBody().getTickets());
  }
//  @Test
//  void shouldMoveTicketToReady() throws Exception {
//    Long[] customers = {123123L};
//    TicketCreationRequest ticketCreationRequest = new TicketCreationRequest("Meshek kuku", "My chickens data is null", 102L, "Angry customer", customers);
//    ResponseEntity<ResponsePayload>
//        createResponseEntity=restTemplate.postForEntity(url+"/ticket",ticketCreationRequest, ResponsePayload.class);
//    assertEquals(CREATED, createResponseEntity.getStatusCode());
//    assertEquals("Ticket successfully created", createResponseEntity.getBody().getMessage());
//    ObjectMapper mapper = new ObjectMapper();
//    TicketResponse ticketResponse = mapper.convertValue(createResponseEntity.getBody().getContext().get(0), TicketResponse.class);
//    Long ticketNumber = ticketResponse.getNumber();
////    ResponseEntity<ResponsePayload>
////        readyResponseEntity=restTemplate.exchange(url+"/ticket/"+ticketNumber+"/ready/201", HttpMethod.PUT,ticketCreationRequest, ResponsePayload.class);
//    ResponseEntity<ResponsePayload>
//        readyResponseEntity=restTemplate.exchange(url,HttpMethod.PUT,ticketCreationRequest,ResponsePayload.class);
//
//  }



}
