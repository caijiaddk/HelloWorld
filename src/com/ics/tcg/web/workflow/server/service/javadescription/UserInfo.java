package com.ics.tcg.web.workflow.server.service.javadescription;

import java.io.Serializable;

public class UserInfo extends DataType implements Serializable
{
	private static final long serialVersionUID = -4104636561926354121L;	
	
	private String userName;//�û�����
	private String userPhoneNum;//�û��绰
	private String userCredentialsNum;//�û����֤����
	
	public String getUserName()
	{
		return userName;
	}
	
	public void setUserName(String userName) 
	{
		this.userName = userName;
	}
	
	public String getUserPhoneNum() 
	{
		return userPhoneNum;
	}
	
	public void setUserPhoneNum(String userPhoneNum) 
	{
		this.userPhoneNum = userPhoneNum;
	}
	
	public String getUserCredentialsNum() 
	{
		return userCredentialsNum;
	}
	
	public void setUserCredentialsNum(String userCredentialsNum)
	{
		this.userCredentialsNum = userCredentialsNum;
	}
}
