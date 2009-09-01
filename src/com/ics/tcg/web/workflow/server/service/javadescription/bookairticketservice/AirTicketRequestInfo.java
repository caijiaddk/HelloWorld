package com.ics.tcg.web.workflow.server.service.javadescription.bookairticketservice;

import java.io.Serializable;
import java.util.Date;

import com.ics.tcg.web.workflow.server.service.javadescription.UserInfo;

public class AirTicketRequestInfo implements Serializable
{
	private static final long serialVersionUID = -5120293488970448861L;
    
	private String airLineName;//���չ�˾�����
	private String ticketKind;//Ʊ������
	private String flightsName;//�������
	private Date   flightDate;//��������
	private Date   takeOffTime;//�ɻ����ʱ��
	private Integer bookCount;//����Ʊ����
	
	private UserInfo userInfo;

	public String getAirLineName() 
	{
		return airLineName;
	}

	public void setAirLineName(String airLineName) 
	{
		this.airLineName = airLineName;
	}

	public String getTicketKind()
	{
		return ticketKind;
	}

	public void setTicketKind(String ticketKind) 
	{
		this.ticketKind = ticketKind;
	}

	public String getFlightsName() 
	{
		return flightsName;
	}

	public void setFlightsName(String flightsName) 
	{
		this.flightsName = flightsName;
	}

	public Date getFlightDate()
	{
		return flightDate;
	}

	public void setFlightDate(Date flightDate)
	{
		this.flightDate = flightDate;
	}
	
	public Date getTakeOffTime() 
	{
		return takeOffTime;
	}

	public void setTakeOffTime(Date takeOffTime)
	{
		this.takeOffTime = takeOffTime;
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
