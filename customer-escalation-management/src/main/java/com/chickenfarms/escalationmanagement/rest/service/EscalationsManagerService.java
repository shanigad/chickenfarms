package com.chickenfarms.escalationmanagement.rest.service;

import com.chickenfarms.escalationmanagement.enums.Status;
import com.chickenfarms.escalationmanagement.model.dto.TicketFilterRequest;
import com.chickenfarms.escalationmanagement.model.entity.Problem;
import com.chickenfarms.escalationmanagement.model.entity.Tag;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.repository.ProblemRepository;
import com.chickenfarms.escalationmanagement.repository.TagRepository;
import com.chickenfarms.escalationmanagement.repository.TicketRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EscalationsManagerService {

  private final TicketRepository ticketRepository;
  private final ProblemRepository problemRepository;


  public Page<Ticket> getFilteredTickets(TicketFilterRequest ticketFilterRequest, int pageNum){
    String status = ticketFilterRequest.getStatus().getStatus();
    Problem problem = problemRepository.getById(ticketFilterRequest.getProblem());
    Page<Ticket> filteredTickets = ticketRepository.getAllByStatusAndProviderAndProblem(status, ticketFilterRequest.getProvider(), problem, PageRequest.of(pageNum, 5));
    return filteredTickets;
  }


}
