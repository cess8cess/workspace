package com.nrc.controller;

import java.util.List;

import com.nrc.model.Ticket;
import com.nrc.model.TicketStatus;

public interface ITicketController {

	

	
	/** 
	 * <i>TICKET CREATION API</i> <p>
	 * 
	 * <b>SupportedMethods:</b> POST <br>
	 * 
	 * <b>Description:</b> This API creates ticket with the lines defined lineNumber parameter<br>
	 *               
	 * <b>RequestSample:</b> http://localhost:9090/ticket?lineNumber=3 <br>
	 *
	 * <b>ResponseSample:</b> 
	 * {"id":1,"ticketLines":[{"numbers":[1,0,1]},{"numbers":[0,1,2]},{"numbers":[1,1,0]}],"checked":false}
	 * <p>
	 * 
	 * @param lineNumber count of the lines
	 * @return Ticket
	 * 
	 */
	
	Ticket createTicket(int lineNumber);

	
	/** 
	 * <i>TICKET FETCH ALL API</i> <p>
	 * 
	 * <b>SupportedMethods:</b> GET <br>
	 * 
	 * <b>Description:</b> This API fetches all ticket data<br>
	 *               
	 * <b>RequestSample:</b> http://localhost:9090/ticket <br>
	 *
	 * <b>ResponseSample:</b> 
	 * [{"id":1,"ticketLines":[{"numbers":[1,0,1]},{"numbers":[0,1,2]},{"numbers":[1,1,0]}],"checked":false},{"id":2,"ticketLines":[{"numbers":[2,1,2]},{"numbers":[0,1,1]},{"numbers":[2,1,0]}],"checked":false},{"id":3,"ticketLines":[{"numbers":[1,1,2]},{"numbers":[0,0,0]},{"numbers":[0,1,2]}],"checked":false}] 
	 * <p>
	 
	 * @return Ticket List
	 * 
	 */
	
	List<Ticket> getAllTickets();

	
	/** 
	 * <i>INDIVIDUAL TICKET FETCH API</i> <p>
	 * 
	 * <b>SupportedMethods:</b> GET <br>
	 * 
	 * <b>Description:</b> This API fetches individual ticket inquired with id parameter<br>
	 *               
	 * <b>RequestSample:</b> http://localhost:9090/ticket/1 <br>
	 *
	 * <b>ResponseSample:</b> 
	 * {"id":1,"ticketLines":[{"numbers":[1,0,1]},{"numbers":[0,1,2]},{"numbers":[1,1,0]}],"checked":false}
	 * <p>
	 * 
	 * @param id long ticketId
	 * @return Ticket
	 * 
	 */
	Ticket getTicketById(long id);



	/** 
	 * <i>AMEND TICKET LINES API</i> <p>
	 * 
	 * <b>SupportedMethods:</b> PUT <br>
	 * 
	 * <b>Description:</b> This API amend ticket lines for individual ticket inquired with id parameter<br>
	 *               
	 * <b>RequestSample:</b> http://localhost:9090/ticket/1?lineNumber=2 <br>
	 *
	 * <b>ResponseSample:</b> 
	 * {"id":4,"ticketLines":[{"numbers":[1,0,1]},{"numbers":[1,0,1]}],"checked":false}
	 * <p>
	 * 
	 * @param id long ticketId
	 * @param lineNumber int count of the lines
	 * @return Ticket
	 * 
	 */
	
	Ticket amendTicket(long id, int lineNumber);

	
	
	/** 
	 * <i>TICKET STATUS CHECK API</i> <p>
	 * 
	 * <b>SupportedMethods:</b> PUT <br>
	 * 
	 * <b>Description:</b> This API retrieve status of ticket for individual ticket inquired with id parameter<br>
	 *               
	 * <b>RequestSample:</b> http://localhost:9090/status/1 <br>
	 *
	 * <b>ResponseSample:</b> 
	 * {"ticketId":1,"resultList":[{"numbers":[1,0,1],"result":10},{"numbers":[1,1,0],"result":10},{"numbers":[0,1,2],"result":1}],"checked":true}
	 * <p>
	 * 
	 * @param id long ticketId
	 * @return TicketStatus
	 * 
	 */
	TicketStatus checkTicket(long id);

}