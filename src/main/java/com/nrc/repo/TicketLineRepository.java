package com.nrc.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nrc.model.TicketLine;

@Repository
public interface TicketLineRepository extends CrudRepository<TicketLine, Long> {

}
