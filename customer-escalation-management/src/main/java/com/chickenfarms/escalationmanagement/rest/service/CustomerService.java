package com.chickenfarms.escalationmanagement.rest.service;

import com.chickenfarms.escalationmanagement.model.entity.CustomerInTicket;
import com.chickenfarms.escalationmanagement.model.entity.CustomerTicketId;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.repository.CustomerInTicketRepository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  @Autowired
  CustomerInTicketRepository customerInTicketRepository;

  public void attachCustomersToTicket(Long[] customers, Ticket ticket, Date createdDate) {
    CustomerInTicket customerInTicket;
    for (Long c: customers) {
      customerInTicket = new CustomerInTicket(new CustomerTicketId(c, ticket), createdDate);
      customerInTicketRepository.save(customerInTicket);
    }
  }
}
