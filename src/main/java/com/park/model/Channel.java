package com.park.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;


public class Channel {
	
	private int id = -1;
	private int parkId;
	private Integer macId;
	private int channelId;
	private int channelFlag;
	private Date date;
	private int status;
	private int isEffective;
	private String description;
	@JsonIgnore
	private int isDeleted;
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public int getIsEffective() {
		return isEffective;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setIsEffective(int isEffective) {
		this.isEffective = isEffective;
	}
	
	
	public Integer getMacId() {
		return macId;
	}

	public int getChannelId() {
		return channelId;
	}

	public int getChannelFlag() {
		return channelFlag;
	}

	public void setMac(Integer mac) {
		this.macId = mac;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public void setChannelFlag(int channelFlag) {
		this.channelFlag = channelFlag;
	}
	
	
	
	public int getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public void setDate(String date) {
		try {
			this.date = new SimpleDateFormat(Constants.DATEFORMAT).parse(date);
		} catch (ParseException e) {		
			e.printStackTrace();
		}
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	public int getParkId() {
		return parkId;
	}
	public void setParkId(int parkId) {
		this.parkId = parkId;
	}
	
	

}
