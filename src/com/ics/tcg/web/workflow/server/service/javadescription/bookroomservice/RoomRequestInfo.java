package com.ics.tcg.web.workflow.server.service.javadescription.bookroomservice;

import java.io.Serializable;
import java.util.Date;

import com.ics.tcg.web.workflow.server.service.javadescription.UserInfo;

public class RoomRequestInfo implements Serializable
{
	private static final long serialVersionUID = -2155343697495606656L;
    
	private String hotelName;//�õ�����
	private String roomKind;//���������
	private Date roomCheckInTime;//������ס��ʱ��
	private Date roomLeaveTime;//�˷���ʱ��
	private Integer roomCount;//��������
	
	private UserInfo userInfo;//������Ŀͻ���Ϣ

	public String getHotelName() 
	{
		return hotelName;
	}

	public void setHotelName(String hotelName)
	{
		this.hotelName = hotelName;
	}

	public String getRoomKind()
	{
		return roomKind;
	}

	public void setRoomKind(String roomKind) 
	{
		this.roomKind = roomKind;
	}

	public Date getRoomCheckInTime()
	{
		return roomCheckInTime;
	}

	public void setRoomCheckInTime(Date roomCheckInTime) 
	{
		this.roomCheckInTime = roomCheckInTime;
	}

	public Date getRoomLeaveTime() {
		return roomLeaveTime;
	}

	public void setRoomLeaveTime(Date roomLeaveTime)
	{
		this.roomLeaveTime = roomLeaveTime;
	}

	public Integer getRoomCount() 
	{
		return roomCount;
	}

	public void setRoomCount(Integer roomCount)
	{
		this.roomCount = roomCount;
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
