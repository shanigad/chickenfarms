package com.chickenfarms.escalationmanagement.repository;

import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketManagerRepository extends JpaRepository<Ticket, Long> {

}
