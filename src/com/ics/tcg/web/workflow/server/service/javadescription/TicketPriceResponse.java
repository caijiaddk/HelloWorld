package com.ics.tcg.web.workflow.server.service.javadescription;

import java.io.Serializable;

public class TicketPriceResponse extends DataType implements Serializable
{
	private static final long serialVersionUID = -7968477986116365185L;
	
	private Float ticketPrice;//Ʊ�ļ۸�

	public Float getTicketPrice() 
	{
		return ticketPrice;
	}

	public void setTicketPrice(Float ticketPrice) 
	{
		this.ticketPrice = ticketPrice;
	}
}
