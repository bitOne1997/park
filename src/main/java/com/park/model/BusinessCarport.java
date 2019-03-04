package com.park.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BusinessCarport {
	private int id;
	private int parkId;
	private int carportNumber;
	private Integer macId;
	private int status;
	private int floor=0;
	private String position="";
	private String description="";
	private Date date;
	
	
	public int getId() {
		return id;
	}
	public int getParkId() {
		return parkId;
	}
	public int getCarportNumber() {
		return carportNumber;
	}
	public Integer getMacId() {
		return macId;
	}
	public int getStatus() {
		return status;
	}
	public int getFloor() {
		return floor;
	}
	public String getPosition() {
		return position;
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
	public void setParkId(int parkId) {
		this.parkId = parkId;
	}
	public void setCarportNumber(int carportNumber) {
		this.carportNumber = carportNumber;
	}
	public void setMacId(Integer macId) {
		this.macId = macId;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public void setPosition(String position) {
		this.position = position;
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
