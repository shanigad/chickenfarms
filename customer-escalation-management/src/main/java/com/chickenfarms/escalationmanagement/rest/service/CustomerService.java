package com.chickenfarms.escalationmanagement.rest.service;

import com.chickenfarms.escalationmanagement.exception.ResourceNotFoundException;
import com.chickenfarms.escalationmanagement.model.entity.CustomerTicket;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.repository.CustomerTicketRepository;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private final CustomerTicketRepository customerTicketRepository;

  public CustomerService(
      CustomerTicketRepository customerTicketRepository) {
    this.customerTicketRepository = customerTicketRepository;
  }

  public void attachCustomersToTicket(Long[] customers, Ticket ticket, Date createdDate) {
    for (Long c: customers) {
      attachCustomerToTicket(c, ticket, createdDate);
    }
  }

  public void attachCustomerToTicket(Long customerId, Ticket ticket, Date addedDate) {
    if(addedDate == null){
      addedDate = new Date();
    }
    CustomerTicket customerTicket = new CustomerTicket(customerId, ticket, addedDate);
    customerTicketRepository.save(customerTicket);
  }

  public ArrayList<Long> getCustomersByTicket(Ticket ticket){
//    List<CustomerTicket> customers = ticket.getCustomers();
    ArrayList<Long> customers = new ArrayList<>();
    customerTicketRepository.findCustomerTicketByTicket(ticket).forEach(
        customerTicket -> customers.add(customerTicket.getCustomerId()));
    return customers;
  }
}
