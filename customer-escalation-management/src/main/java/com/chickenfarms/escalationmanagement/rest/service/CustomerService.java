package com.chickenfarms.escalationmanagement.rest.service;

import com.chickenfarms.escalationmanagement.model.entity.CustomerTicket;
import com.chickenfarms.escalationmanagement.model.entity.CustomerTicketKey;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.repository.CustomerTicketRepository;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  @Autowired
  CustomerTicketRepository customerTicketRepository;

  public void attachCustomersToTicket(Long[] customers, Ticket ticket, Date createdDate) {
    CustomerTicket customerTicket;
    for (Long c: customers) {
      customerTicket = new CustomerTicket(new CustomerTicketKey(c, ticket), createdDate);
      customerTicketRepository.save(customerTicket);
    }
  }

  public ArrayList<Long> getCustomersByTicket(Ticket ticket){
    ArrayList<Long> customers = new ArrayList<>();
    customerTicketRepository.findCustomerInTicketByCustomerTicketId_Ticket(ticket).forEach(
        customerTicket -> customers.add(customerTicket.getCustomerTicketKey().getCustomerId()));

    return customers;
  }
}
