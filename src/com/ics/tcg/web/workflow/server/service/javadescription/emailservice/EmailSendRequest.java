package com.ics.tcg.web.workflow.server.service.javadescription.emailservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.ics.tcg.web.workflow.server.service.javadescription.DataType;

public class EmailSendRequest extends DataType implements Serializable
{
	private static final long serialVersionUID = -1282693952746470711L;
    
	private String  emailSender;
	private String  emailContent;
	private Date    emailSendTime;
	
	private String receiversEmail[];
	
	public EmailSendRequest()
	{
		emailSender = new String();
		emailContent = new String();
		emailSendTime = new Date();
		receiversEmail = (String[])new ArrayList<String>().toArray();
	}

	public String getEmailSender()
	{
		return emailSender;
	}

	public void setEmailSender(String emailSender) 
	{
		this.emailSender = emailSender;
	}

	public Date getEmailSendTime() 
	{
		return emailSendTime;
	}

	public void setEmailSendTime(Date emailSendTime) 
	{
		this.emailSendTime = emailSendTime;
	}

	public String getEmailContent() 
	{
		return emailContent;
	}

	public void setEmailContent(String emailContent)
	{
		this.emailContent = emailContent;
	}

	public String[] getReceiversEmail() 
	{
		return receiversEmail;
	}

	public void setReceiversEmail(String[] receiversEmail) 
	{
		this.receiversEmail = receiversEmail;
	}
}
