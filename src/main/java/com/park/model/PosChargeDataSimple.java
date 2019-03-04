package com.park.model;

import java.util.Date;

public class PosChargeDataSimple {

	int id;
	
	String cardNumber;
	
	double unPaidMoney;
	
	Date exitDate;

	public Date getExitDate() {
		return exitDate;
	}

	public void setExitDate(Date exitDate) {
		this.exitDate = exitDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public double getUnPaidMoney() {
		return unPaidMoney;
	}

	public void setUnPaidMoney(double unPaidMoney) {
		this.unPaidMoney = unPaidMoney;
	}
}
