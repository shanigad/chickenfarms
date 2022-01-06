package com.chickenfarms.escalationmanagement;

import com.chickenfarms.escalationmanagement.model.payload.CloseTicketRequest;
import com.chickenfarms.escalationmanagement.model.payload.ResponsePayload;
import com.chickenfarms.escalationmanagement.model.payload.TicketCreationRequest;
import com.chickenfarms.escalationmanagement.model.payload.TicketResponse;
import com.chickenfarms.escalationmanagement.model.payload.TicketsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EscalationManagementApplicationTests {


  @LocalServerPort
  int randomServerPort;

  private RestTemplate restTemplate;
  private String singelTicketUrl;
  private String escalationManagementUrl;


  @BeforeEach
  void setUp(){
    restTemplate = new RestTemplate();
    singelTicketUrl = "http://localhost:" + randomServerPort + "/ticket/";
    escalationManagementUrl = "http://localhost:" + randomServerPort + "/escalation-management";
  }

  @Test
  void shouldCreateNewTicket() throws Exception {
    Long[] customers = {123123L};
    TicketCreationRequest ticketCreationRequest = new TicketCreationRequest("Meshek kuku", "My chickens data is null", 102L, "Angry customer", customers);
    ResponseEntity<ResponsePayload>
        responseEntity=restTemplate.postForEntity(escalationManagementUrl +"/ticket",ticketCreationRequest, ResponsePayload.class);
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
    Long[] customers = {125L};
    TicketCreationRequest
        createdTicket1 = new TicketCreationRequest("filterProvider", "description", 101L, "Shani1", customers);
    ResponseEntity<ResponsePayload> createResponseEntity=restTemplate.postForEntity(
        escalationManagementUrl +"/ticket",createdTicket1, ResponsePayload.class);
    assertEquals(CREATED, createResponseEntity.getStatusCode());

    ResponseEntity<TicketsResponse>
        responseEntity=restTemplate.getForEntity(escalationManagementUrl +"/tickets/filter/0?status=Created&tag=null&provider=filterProvider&problem=1&rootCause=null" ,TicketsResponse.class);
    assertEquals(OK, responseEntity.getStatusCode());
    assertEquals(0, responseEntity.getBody().getPage());
    assertNotEquals(0, responseEntity.getBody().getTickets().size());
  }

  @Test
  void shouldMoveTicketToReady() throws Exception {
    Long[] customers = {123123L};
    TicketCreationRequest ticketCreationRequest = new TicketCreationRequest("Meshek kuku", "My chickens data is null", 102L, "Angry customer", customers);
    ResponseEntity<ResponsePayload>
        createResponseEntity=restTemplate.postForEntity(
        escalationManagementUrl +"/ticket",ticketCreationRequest, ResponsePayload.class);
    assertEquals(CREATED, createResponseEntity.getStatusCode());

    ObjectMapper mapper = new ObjectMapper();
    TicketResponse ticketResponse = mapper.convertValue(createResponseEntity.getBody().getContext().get(0), TicketResponse.class);
    Long ticketNumber = ticketResponse.getNumber();

    ResponseEntity<ResponsePayload>
        readyResponseEntity=restTemplate.exchange(singelTicketUrl +ticketNumber+"/ready/201", HttpMethod.PUT,null, ResponsePayload.class);
    assertEquals(OK, readyResponseEntity.getStatusCode());
    assertEquals("Ticket updated to status 'Ready'", readyResponseEntity.getBody().getMessage());
    ticketResponse = mapper.convertValue(readyResponseEntity.getBody().getContext().get(0), TicketResponse.class);
    assertEquals("Ready", ticketResponse.getStatus());
    assertEquals("Chicken is dead", ticketResponse.getRootCause());
  }

  @Test
  void shouldGetCreatedTicket() throws Exception {
    Long[] customers = {123123L};
    TicketCreationRequest ticketCreationRequest = new TicketCreationRequest("Meshek kuku", "My chickens data is null", 102L, "Angry customer", customers);
    ResponseEntity<ResponsePayload>
        createResponseEntity=restTemplate.postForEntity(
        escalationManagementUrl +"/ticket",ticketCreationRequest, ResponsePayload.class);
    assertEquals(CREATED, createResponseEntity.getStatusCode());

    ObjectMapper mapper = new ObjectMapper();
    TicketResponse ticketResponse = mapper.convertValue(createResponseEntity.getBody().getContext().get(0), TicketResponse.class);
    Long ticketNumber = ticketResponse.getNumber();

    ResponseEntity<TicketResponse> responseEntity = restTemplate.getForEntity(
        singelTicketUrl +ticketNumber, TicketResponse.class);
    assertEquals(OK, responseEntity.getStatusCode());
    assertEquals(ticketNumber, responseEntity.getBody().getNumber());


  }

  @Test
  void shouldCloseReadyTicket() throws Exception {
    //Create Ticket
    Long[] customers = {123123L};
    TicketCreationRequest ticketCreationRequest =
        new TicketCreationRequest("Tnuva", "issue description", 101L, "Happy customer", customers);
    ResponseEntity<ResponsePayload>
        createResponseEntity =
        restTemplate.postForEntity(escalationManagementUrl + "/ticket", ticketCreationRequest,
            ResponsePayload.class);
    assertEquals(CREATED, createResponseEntity.getStatusCode());
    ObjectMapper mapper = new ObjectMapper();
    TicketResponse ticketResponse =
        mapper.convertValue(createResponseEntity.getBody().getContext().get(0),
            TicketResponse.class);
    Long ticketNumber = ticketResponse.getNumber();

    //Move Ticket to Ready
    ResponseEntity<ResponsePayload>
        readyResponseEntity =
        restTemplate.exchange(singelTicketUrl + ticketNumber + "/ready/203", HttpMethod.PUT, null,
            ResponsePayload.class);
    assertEquals(OK, readyResponseEntity.getStatusCode());
    assertEquals("Ticket updated to status 'Ready'", readyResponseEntity.getBody().getMessage());
    ticketResponse = mapper.convertValue(readyResponseEntity.getBody().getContext().get(0),
        TicketResponse.class);
    assertEquals("Ready", ticketResponse.getStatus());
    ticketNumber = ticketResponse.getNumber();

    //Close Ticket
    CloseTicketRequest closeTicketRequest = new CloseTicketRequest("Found new chicks",true);
    HttpEntity<CloseTicketRequest> requestEntity = new HttpEntity<CloseTicketRequest>(closeTicketRequest, null);
    readyResponseEntity=restTemplate.exchange(singelTicketUrl + ticketNumber + "/close", HttpMethod.PUT,requestEntity, ResponsePayload.class);
    assertEquals(OK, readyResponseEntity.getStatusCode());
    assertEquals("Ticket updated to status 'Closed'", readyResponseEntity.getBody().getMessage());
    ticketResponse = mapper.convertValue(readyResponseEntity.getBody().getContext().get(0),
        TicketResponse.class);
    assertEquals("Closed", ticketResponse.getStatus());
    assertTrue( ticketResponse.isResolved());
  }

  }
