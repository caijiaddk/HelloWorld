package com.ics.tcg.web.workflow.server.service.javadescription.bookbusticketservice;

import java.io.Serializable;
import java.util.Date;

import com.ics.tcg.web.workflow.server.service.javadescription.UserInfo;

public class BusTicketRequestInfo implements Serializable
{
	private static final long serialVersionUID = -3872280597122387023L;
    
	private String busTicketAgentName;//����������
	private String ticketKind;//Ʊ������
	private String busNum;//��ĳ���
	private Date   busDate;//����������
	private Integer bookCount;//����Ʊ����
	
	private UserInfo userInfo;//�����ߵ���Ϣ

	public String getBusTicketAgentName() 
	{
		return busTicketAgentName;
	}

	public void setBusTicketAgentName(String busTicketAgentName)
	{
		this.busTicketAgentName = busTicketAgentName;
	}

	public String getTicketKind() 
	{
		return ticketKind;
	}

	public void setTicketKind(String ticketKind) 
	{
		this.ticketKind = ticketKind;
	}

	public String getBusNum()
	{
		return busNum;
	}

	public void setBusNum(String busNum) 
	{
		this.busNum = busNum;
	}

	public Date getBusDate() 
	{
		return busDate;
	}

	public void setBusDate(Date busDate) 
	{
		this.busDate = busDate;
	}

	public Integer getBookCount()
	{
		return bookCount;
	}

	public void setBookCount(Integer bookCount)
	{
		this.bookCount = bookCount;
	}

	public UserInfo getUserInfo() 
	{
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) 
	{
		this.userInfo = userInfo;
	}
}
