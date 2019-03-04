package com.park.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hardware {
	private int id;
	private String mac;
	private int type;
	private int status;
	private String description;
	private Date date;
	
	
	public int getId() {
		return id;
	}
	public String getMac() {
		return mac;
	}
	public int getType() {
		return type;
	}
	public int getStatus() {
		return status;
	}
	public String getDescription() {
		return description;
	}
	public Date getDate() {
		return date;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setMac(String mac) {
		this.mac = mac.trim();
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setDate(String date) {
		try {
			this.date = new SimpleDateFormat(Constants.DATEFORMAT).parse(date);
		} catch (ParseException e) {		
			e.printStackTrace();
		}
	}

}
