package com.nrc.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nrc.model.Ticket;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {

}
