package com.park.model;

import java.util.Date;

public class CarportExpense {
	int id;
	String parkName;
	String cardNumber;
	String carportNumber;
	int Occupied;
	Date startTime;
	Date endTime;
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCarportNumber() {
		return carportNumber;
	}
	public void setCarportNumber(String carportNumber) {
		this.carportNumber = carportNumber;
	}
	public int getOccupied() {
		return Occupied;
	}
	public void setOccupied(int occupied) {
		Occupied = occupied;
	}
	
	

}
