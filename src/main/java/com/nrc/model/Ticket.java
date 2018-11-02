package com.nrc.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tickets")
@SequenceGenerator(name = "ticketsSeq")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "checked")
	private boolean isChecked = false;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<TicketLine> ticketLines;

	public Ticket(List<TicketLine> ticketLines) {
		super();
		this.ticketLines = ticketLines;
	}

	public Ticket(List<TicketLine> ticketLines, long id) {
		super();
		this.id = id;
		this.ticketLines = ticketLines;
	}

	public Ticket() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public List<TicketLine> getTicketLines() {
		return ticketLines;
	}

	public void setTicketLines(List<TicketLine> ticketLines) {
		this.ticketLines = ticketLines;
	}

}
