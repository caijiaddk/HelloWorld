package com.ics.tcg.web.workflow.server.service.javadescription.booktrainticketservice;

import java.io.Serializable;
import java.util.Date;

import com.ics.tcg.web.workflow.server.service.javadescription.UserInfo;

public class TrainTicketRequestInfo implements Serializable
{
	private static final long serialVersionUID = -8781137668480877687L;
    
	private String trianTicketAgentName;//��Ʊ��������
	private String ticketKind;//��Ʊ����
	private Date   trainTime;//�𳵷���ʱ��
	private String trainNum;//�𳵳���
	private Integer bookCount;//������Ʊ����
	
	private UserInfo userInfo;//��������Ϣ

	public String getTrianTicketAgentName() 
	{
		return trianTicketAgentName;
	}

	public void setTrianTicketAgentName(String trianTicketAgentName) 
	{
		this.trianTicketAgentName = trianTicketAgentName;
	}

	public String getTicketKind() 
	{
		return ticketKind;
	}

	public void setTicketKind(String ticketKind) 
	{
		this.ticketKind = ticketKind;
	}

	public Date getTrainTime() 
	{
		return trainTime;
	}

	public void setTrainTime(Date trainTime) 
	{
		this.trainTime = trainTime;
	}

	public String getTrainNum() 
	{
		return trainNum;
	}

	public void setTrainNum(String trainNum) 
	{
		this.trainNum = trainNum;
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
