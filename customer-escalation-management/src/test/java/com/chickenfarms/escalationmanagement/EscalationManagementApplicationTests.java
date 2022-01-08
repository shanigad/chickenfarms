package com.chickenfarms.escalationmanagement;

import com.chickenfarms.escalationmanagement.model.payload.CloseTicketRequest;
import com.chickenfarms.escalationmanagement.model.payload.PostCommentRequest;
import com.chickenfarms.escalationmanagement.model.payload.ResponsePayload;
import com.chickenfarms.escalationmanagement.model.payload.TicketCreationRequest;
import com.chickenfarms.escalationmanagement.model.payload.TicketResponse;
import com.chickenfarms.escalationmanagement.model.payload.TicketUpdateRequest;
import com.chickenfarms.escalationmanagement.model.payload.TicketsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
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
  private ObjectMapper mapper;

  @BeforeEach
  void setUp(){
    restTemplate = new RestTemplate();
    singelTicketUrl = "http://localhost:" + randomServerPort + "/ticket/";
    escalationManagementUrl = "http://localhost:" + randomServerPort + "/escalation-management";
    mapper = new ObjectMapper();
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
    //Create Ticket
    Long[] customers = {125L};
    TicketCreationRequest
        ticketCreationRequest = new TicketCreationRequest("filterProvider", "description", 101L, "Shani1", customers);
    createTicket(ticketCreationRequest);

    //Get Tickets
    ResponseEntity<TicketsResponse>
        responseEntity=restTemplate.getForEntity(escalationManagementUrl +"/tickets/filter/0?status=null&tag=null&provider=null&problem=null&rootCause=null" ,TicketsResponse.class);
    assertEquals(OK, responseEntity.getStatusCode());
    assertEquals(0, responseEntity.getBody().getPage());
    assertNotEquals(0, responseEntity.getBody().getTickets().size());
  }

  @Test
  void shouldMoveTicketToReady() throws Exception {
    //Create Ticket
    Long[] customers = {123123L};
    TicketCreationRequest ticketCreationRequest = new TicketCreationRequest("Meshek kuku", "My chickens data is null", 102L, "Angry customer", customers);
    Long ticketNumber = createTicket(ticketCreationRequest);

    //Move Ticket to Ready
    ResponseEntity<ResponsePayload>
        readyResponseEntity=restTemplate.exchange(singelTicketUrl +ticketNumber+"/ready/201", HttpMethod.PUT,null, ResponsePayload.class);
    assertEquals(OK, readyResponseEntity.getStatusCode());
    assertEquals("Ticket updated to status 'Ready'", readyResponseEntity.getBody().getMessage());
    TicketResponse ticketResponse = mapper.convertValue(readyResponseEntity.getBody().getContext().get(0), TicketResponse.class);
    assertEquals("Ready", ticketResponse.getStatus());
    assertEquals("Chicken is dead", ticketResponse.getRootCause());
  }

  @Test
  void shouldGetCreatedTicket() throws Exception {
    Long[] customers = {123123L};
    TicketCreationRequest ticketCreationRequest = new TicketCreationRequest("Meshek kuku", "My chickens data is null", 102L, "Angry customer", customers);
    Long ticketNumber = createTicket(ticketCreationRequest);

    ResponseEntity<TicketResponse> responseEntity = restTemplate.getForEntity(
        singelTicketUrl +ticketNumber, TicketResponse.class);
    assertEquals(OK, responseEntity.getStatusCode());
    assertEquals(ticketNumber, responseEntity.getBody().getTicketNumber());
  }

  @Test
  void shouldCloseReadyTicket() throws Exception {
    //Create Ticket
    Long[] customers = {123123L};
    TicketCreationRequest ticketCreationRequest =
        new TicketCreationRequest("Tnuva", "issue description", 101L, "Happy customer", customers);
    Long ticketNumber = createTicket(ticketCreationRequest);
    //Move Ticket to Ready
    ResponseEntity<ResponsePayload>
        readyResponseEntity =
        restTemplate.exchange(singelTicketUrl + ticketNumber + "/ready/203", HttpMethod.PUT, null,
            ResponsePayload.class);
    assertEquals(OK, readyResponseEntity.getStatusCode());
    assertEquals("Ticket updated to status 'Ready'", readyResponseEntity.getBody().getMessage());
    TicketResponse ticketResponse = mapper.convertValue(readyResponseEntity.getBody().getContext().get(0),
        TicketResponse.class);
    assertEquals("Ready", ticketResponse.getStatus());
    ticketNumber = ticketResponse.getTicketNumber();

    //Close Ticket
    CloseTicketRequest closeTicketRequest = new CloseTicketRequest("Found new chicks",true);
    HttpEntity<CloseTicketRequest> requestEntity = new HttpEntity<>(closeTicketRequest, null);
    readyResponseEntity = restTemplate.exchange(singelTicketUrl + ticketNumber + "/close", HttpMethod.PUT,requestEntity, ResponsePayload.class);
    assertEquals(OK, readyResponseEntity.getStatusCode());
    assertEquals("Ticket updated to status 'Closed'", readyResponseEntity.getBody().getMessage());
    ticketResponse = mapper.convertValue(readyResponseEntity.getBody().getContext().get(0),
        TicketResponse.class);
    assertEquals("Closed", ticketResponse.getStatus());
    assertTrue( ticketResponse.isResolved());
  }

  @Test
  void shouldPostCommentAndViewComments() throws Exception {
    Long[] customers = {123123L};
    TicketCreationRequest ticketCreationRequest =
        new TicketCreationRequest("Tnuva", "issue description", 101L, "Happy customer", customers);
    Long ticketNumber = createTicket(ticketCreationRequest);

    // Post comment
    PostCommentRequest postCommentRequest = new PostCommentRequest("I'm a comment", "Shani");
    ResponseEntity<ResponsePayload> responseEntity = restTemplate.postForEntity(singelTicketUrl + ticketNumber + "/comment", postCommentRequest, ResponsePayload.class);
    assertEquals(CREATED, responseEntity.getStatusCode());
    assertEquals("Posted comment to Ticket", responseEntity.getBody().getMessage());
    assertTrue(responseEntity.getBody().getContext().get(0).toString().contains("I'm a comment"));
  }

  @Test
  void shouldUpdateTicket() throws Exception {
    Long[] customers = {123123L};
    TicketCreationRequest ticketCreationRequest =
        new TicketCreationRequest("Tnuva", "issue description", 101L, "Happy customer", customers);
    Long ticketNumber = createTicket(ticketCreationRequest);
    // Update description
    TicketUpdateRequest ticketUpdateRequest = new TicketUpdateRequest("update description", null);
    HttpEntity<TicketUpdateRequest> requestEntity = new HttpEntity<>(ticketUpdateRequest, null);
    ResponseEntity<ResponsePayload>
        responseEntity = restTemplate.exchange(singelTicketUrl + ticketNumber, HttpMethod.PUT,requestEntity, ResponsePayload.class);
    assertEquals(OK, responseEntity.getStatusCode());
      assertEquals("Ticket updated successfully", responseEntity.getBody().getMessage());
    TicketResponse ticketResponse =
        mapper.convertValue(responseEntity.getBody().getContext().get(0),
            TicketResponse.class);
    assertEquals("update description", ticketResponse.getDescription());
    assertEquals("General Error", ticketResponse.getProblem());
    assertEquals(ticketNumber, ticketResponse.getTicketNumber());
  }
  
  @Test
  void shouldSplitTicket() throws Exception {
    Long[] customers = {123123L, 456456L};
    TicketCreationRequest ticketCreationRequest =
        new TicketCreationRequest("Tnuva", "issue description", 101L, "Happy customer", customers);
    Long ticketNumber = createTicket(ticketCreationRequest);
    //Split ticket
    ResponseEntity<ResponsePayload>
        splitResponseEntity =
        restTemplate.postForEntity(singelTicketUrl + ticketNumber + "/split/201/456456", null,
            ResponsePayload.class);
    assertEquals(CREATED, splitResponseEntity.getStatusCode());
    TicketResponse ticketResponse =
        mapper.convertValue(splitResponseEntity.getBody().getContext().get(0),
            TicketResponse.class);
    assertEquals("Ticket number " + ticketNumber + " successfully split to Ticket number " + ticketResponse.getTicketNumber(), splitResponseEntity.getBody().getMessage());
    assertEquals("General Error", ticketResponse.getProblem());
    assertEquals("Chicken is dead", ticketResponse.getRootCause());
    assertEquals(1, ticketResponse.getImpact());
    assertEquals("Ready", ticketResponse.getStatus());

  }

  @Test
  void shouldSplitTicketAndReconcile() throws Exception {
    Long[] customers = {123123L, 456456L};
    TicketCreationRequest ticketCreationRequest =
        new TicketCreationRequest("Yashek", "issue description", 101L, "Happy customer", customers);
    Long ticketNumber = createTicket(ticketCreationRequest);
    //Move Ticket to Ready
    ResponseEntity<ResponsePayload>
        readyResponseEntity =
        restTemplate.exchange(singelTicketUrl + ticketNumber + "/ready/201", HttpMethod.PUT, null,
            ResponsePayload.class);
    assertEquals(OK, readyResponseEntity.getStatusCode());
    assertEquals("Ticket updated to status 'Ready'", readyResponseEntity.getBody().getMessage());
    TicketResponse ticketResponse = mapper.convertValue(readyResponseEntity.getBody().getContext().get(0),
        TicketResponse.class);
    assertEquals("Ready", ticketResponse.getStatus());
    ticketNumber = ticketResponse.getTicketNumber();

    // Split with the same root cause
    ResponseEntity<ResponsePayload>
        splitResponseEntity =
        restTemplate.postForEntity(singelTicketUrl + ticketNumber + "/split/201/456456", null,
            ResponsePayload.class);
    assertEquals(CREATED, splitResponseEntity.getStatusCode());
    ticketResponse =
        mapper.convertValue(splitResponseEntity.getBody().getContext().get(0),
            TicketResponse.class);
    assertEquals("Ticket number " + ticketNumber + " successfully split to Ticket number " + ticketResponse.getTicketNumber(), splitResponseEntity.getBody().getMessage());
    assertEquals("General Error", ticketResponse.getProblem());
    assertEquals("Chicken is dead", ticketResponse.getRootCause());
    assertEquals(1, ticketResponse.getImpact());
    assertEquals("Reconciled", ticketResponse.getStatus());

  }
  @Test
  void shouldAddExistingAndNoneExistingTag() throws Exception {
    Long[] customers = {123123L};
    TicketCreationRequest ticketCreationRequest =
        new TicketCreationRequest("Tnuva", "issue description", 101L, "Happy customer", customers);
    Long ticketNumber = createTicket(ticketCreationRequest);
    // Add non existing Tag
    ResponseEntity<ResponsePayload>
        addTagResponseEntity =
        restTemplate.postForEntity(singelTicketUrl + ticketNumber + "/tag?tagName=test", null,
            ResponsePayload.class);
    assertEquals(OK, addTagResponseEntity.getStatusCode());
    assertEquals("Tag added successfully", addTagResponseEntity.getBody().getMessage());
    TicketResponse ticketResponse =
        mapper.convertValue(addTagResponseEntity.getBody().getContext().get(0),
            TicketResponse.class);
    assertEquals(1, ticketResponse.getTags().size());
    assertTrue( ticketResponse.getTags().contains("test"));

    // Add existing Tag - no change
    addTagResponseEntity =
        restTemplate.postForEntity(singelTicketUrl + ticketNumber + "/tag?tagName=test", null,
            ResponsePayload.class);
    assertEquals(OK, addTagResponseEntity.getStatusCode());
    assertEquals("Tag added successfully", addTagResponseEntity.getBody().getMessage());
    ticketResponse =
        mapper.convertValue(addTagResponseEntity.getBody().getContext().get(0),
            TicketResponse.class);
    assertEquals(1, ticketResponse.getTags().size());
    assertTrue( ticketResponse.getTags().contains("test"));

  }
  @Test
  void shouldAddAndGetCustomers() throws Exception {
    Long[] customers = {123123L};
    TicketCreationRequest ticketCreationRequest =
        new TicketCreationRequest("Tnuva", "issue description", 101L, "Happy customer", customers);
    Long ticketNumber = createTicket(ticketCreationRequest);
    // Get Customers
    ResponseEntity<List<Long>> responseEntity =
        restTemplate.exchange(singelTicketUrl +ticketNumber+ "/customers" ,
            HttpMethod.GET, null, new ParameterizedTypeReference<List<Long>>() {
            });
    assertEquals(OK, responseEntity.getStatusCode());
    assertEquals(1, responseEntity.getBody().size());
    assertEquals(123123L, responseEntity.getBody().get(0));
    // Add customer Customers
    ResponseEntity<ResponsePayload>
        addCustomerResponseEntity =
        restTemplate.postForEntity(singelTicketUrl + ticketNumber + "/customer/456456", null,
            ResponsePayload.class);
    assertEquals(OK, addCustomerResponseEntity.getStatusCode());
    assertEquals("Customer added successfully", addCustomerResponseEntity.getBody().getMessage());
    assertEquals(2, addCustomerResponseEntity.getBody().getContext().size());
    TicketResponse ticketResponse =
        mapper.convertValue(addCustomerResponseEntity.getBody().getContext().get(0),
            TicketResponse.class);
    assertEquals(2, ticketResponse.getImpact());

    // Get Customers
   responseEntity =
        restTemplate.exchange(singelTicketUrl +ticketNumber+ "/customers" ,
            HttpMethod.GET, null, new ParameterizedTypeReference<List<Long>>() {
            });
    assertEquals(OK, responseEntity.getStatusCode());
    assertEquals(2, responseEntity.getBody().size());
    assertEquals(123123L, responseEntity.getBody().get(0));
    assertEquals(456456L, responseEntity.getBody().get(1));
  }
//  @Test
//  void shouldGetNextTicket() throws Exception {}

  private Long createTicket(TicketCreationRequest ticketCreationRequest){
    ResponseEntity<ResponsePayload>
        createResponseEntity =
        restTemplate.postForEntity(escalationManagementUrl + "/ticket", ticketCreationRequest,
            ResponsePayload.class);
    assertEquals(CREATED, createResponseEntity.getStatusCode());
    TicketResponse ticketResponse =
        mapper.convertValue(createResponseEntity.getBody().getContext().get(0),
            TicketResponse.class);
    return ticketResponse.getTicketNumber();
  }
  }


