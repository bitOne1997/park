package com.park.model;

import java.util.Date;

public class CarportStatusDetail {

	private int id;
	private int carportId;
	private int carportNumber;
	
	private Date startTime;
	private Date endTime;
	private int charged;
	private double expense;
	private double actualExpense;
	
	public int getId() {
		return id;
	}
	public int getCarportId() {
		return carportId;
	}
	
	public int getCarportNumber() {
		return carportNumber;
	}
	public void setCarportNumber(int carportNumber) {
		this.carportNumber = carportNumber;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public int getCharged() {
		return charged;
	}
	public double getExpense() {
		return expense;
	}
	public double getActualExpense() {
		return actualExpense;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setCarportId(int carportId) {
		this.carportId = carportId;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setCharged(int charged) {
		this.charged = charged;
	}
	public void setExpense(double expense) {
		this.expense = expense;
	}
	public void setActualExpense(double actualExpense) {
		this.actualExpense = actualExpense;
	}
	
	
	
}
