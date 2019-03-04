package com.park.model;

import java.util.Date;

public class BusinessCarportDetailSimple {
	private int carportNumber;
	private int status;
	public int getCarportNumber() {
		return carportNumber;
	}
	public void setCarportNumber(int carportNumber) {
		this.carportNumber = carportNumber;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	private Date date;
}
