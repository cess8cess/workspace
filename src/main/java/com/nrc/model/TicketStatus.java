package com.nrc.model;

import java.util.ArrayList;
import java.util.List;

public class TicketStatus {
	private long ticketId;
	private boolean isChecked;
	private List<TicketLineStatus> resultList = new ArrayList<>();

	public TicketStatus(Ticket ticket) {
		this.ticketId = ticket.getId();
		this.isChecked = ticket.isChecked();

		ticket.getTicketLines().forEach(line -> resultList.add(new TicketLineStatus(line)));

	}

	public TicketStatus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getTicketId() {
		return ticketId;
	}

	public void setTicketId(long ticketId) {
		this.ticketId = ticketId;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public List<TicketLineStatus> getResultList() {
		return resultList;
	}

	public void setResultList(List<TicketLineStatus> resultList) {
		this.resultList = resultList;
	}

}
