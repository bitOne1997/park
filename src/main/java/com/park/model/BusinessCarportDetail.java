package com.park.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BusinessCarportDetail {
	private int id;
	private int parkId;
	private String parkName;
	private int carportNumber;
	private Integer macId;
	private String mac;
	private int status;
	private int floor;
	private String position;
	private String description;
	private Date date;
	
	
	public int getId() {
		return id;
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

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public int getParkId() {
		return parkId;
	}

	public String getMac() {
		return mac;
	}

	public void setParkId(int parkId) {
		this.parkId = parkId;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
