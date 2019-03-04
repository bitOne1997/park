package com.park.model;

import java.util.Date;

public class BusinessCarportStatus {
	private int id;

	private String parkName;
	private String cardNumber;
	private int carportNumber;
	private String mac;
	private Double posCharge=(double) 0;
	private Double posRealCharge=(double) 0;
	private Double hardwareCharge=(double) 0;
	private Double usage;

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

	public int getCarportNumber() {
		return carportNumber;
	}

	public void setCarportNumber(int carportNumber) {
		this.carportNumber = carportNumber;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Double getPosCharge() {
		return posCharge;
	}

	public void setPosCharge(Double posCharge) {
		this.posCharge = posCharge;
	}

	public Double getPosRealCharge() {
		return posRealCharge;
	}

	public void setPosRealCharge(Double posRealCharge) {
		this.posRealCharge = posRealCharge;
	}

	public Double getHardwareCharge() {
		return hardwareCharge;
	}

	public void setHardwareCharge(Double hardwareCharge) {
		this.hardwareCharge = hardwareCharge;
	}

	public Double getUsage() {
		return usage;
	}

	public void setUsage(Double usage) {
		this.usage = usage;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	private int status;
	private String position;
	private String description;
	private Date date;
}
