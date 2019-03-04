package com.park.model;

import org.codehaus.jackson.annotate.JsonIgnore;

public class DataUsageCardDetail {
	
	@JsonIgnore
	private int id = -1;
	
	private String parkName;
	
	private String cardNumber;
	
	private String phoneNumber;
	
	private int type;
	
	private String position;
	
	private double longitude;
	
	private double latitude;
	
	private int status;

	private int parkId;
	
	private float dataUsage;
		
	

	public int getId() {
		return id;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public int getType() {
		return type;
	}

	public int getStatus() {
		return status;
	}

	public float getDataUsage() {
		return dataUsage;
	}

	public int getParkId() {
		return parkId;
	}

	public String getPosition() {
		return position;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setDataUsage(float dataUsage) {
		this.dataUsage = dataUsage;
	}

	public void setParkId(int parkId) {
		this.parkId = parkId;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setLongitude(double longtitude) {
		this.longitude = longtitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	

}
