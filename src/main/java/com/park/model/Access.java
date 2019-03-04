package com.park.model;


import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;


public class Access {
	
	@JsonIgnore
	private int id;
	private int channelId;
	@JsonIgnore
	private Date date;
	@JsonIgnore
	private int isDeleted;
	
	
	public int getId() {
		return id;
	}
	public int getChannelId() {
		return channelId;
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
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	

}
