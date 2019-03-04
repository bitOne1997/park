package com.park.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PosChargeData {

	int id;
	
	String cardNumber="";
	
	int parkId=4;
	
	String parkDesc="";
	
	String portNumber="0";
	
	String rejectReason="";
//0是支付宝 1是微信 2是现金
    int payType=2;
	
	boolean isEntrance=true;
	
	String operatorId ="operator";
	
	String posId ="posid";
	
	double chargeMoney=0;
	
	double paidMoney=0.0;
	
	double unPaidMoney=0.0;
	
	double givenMoney=0.0;

	double changeMoney=0.0;
	
	int isOneTimeExpense=0;
	
	boolean paidCompleted=false;
	
	boolean isLargeCar=false;
	
	Date entranceDate;
	
	Date exitDate;

	String url="";
	
    private Double discount=0.0;

    private Byte discountType=0;

    private Double other=0.0;

    private String other2="";
    
    private String outUrl="";

	public int getId() {
		return id;
	}

	

	public String getOutUrl() {
		return outUrl;
	}



	public void setOutUrl(String outUrl) {
		this.outUrl = outUrl;
	}



	public void setId(int id) {
		this.id = id;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	} 
	
	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Byte getDiscountType() {
		return discountType;
	}

	public void setDiscountType(Byte discountType) {
		this.discountType = discountType;
	}

	public Double getOther() {
		return other;
	}

	public void setOther(Double other) {
		this.other = other;
	}

	public String getOther2() {
		return other2;
	}

	public void setOther2(String other2) {
		this.other2 = other2;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public int getParkId() {
		return parkId;
	}

	public void setParkId(int parkId) {
		this.parkId = parkId;
	}

	public String getParkDesc() {
		return parkDesc;
	}

	public void setParkDesc(String parkDesc) {
		this.parkDesc = parkDesc;
	}

	public String getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	public boolean isEntrance() {
		return isEntrance;
	}

	public void setEntrance(boolean isEntrance) {
		this.isEntrance = isEntrance;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public double getChargeMoney() {
		return chargeMoney;
	}

	public void setChargeMoney(double chargeMoney) {
		this.chargeMoney = chargeMoney;
	}

	public double getPaidMoney() {
		return paidMoney;
	}

	public void setPaidMoney(double paidMoney) {
		this.paidMoney = paidMoney;
	}

	public double getUnPaidMoney() {
		return unPaidMoney;
	}

	public void setUnPaidMoney(double unPaidMoney) {
		this.unPaidMoney = unPaidMoney;
	}

	
	public double getGivenMoney() {
		return givenMoney;
	}

	public void setGivenMoney(double givenMoney) {
		this.givenMoney = givenMoney;
	}
	
	public double getChangeMoney() {
		return changeMoney;
	}

	public void setChangeMoney(double changeMoney) {
		this.changeMoney = changeMoney;
	}

	public Date getEntranceDate() {
		return entranceDate;
	}

	public void setEntranceDate(String entranceDate) throws ParseException {
		this.entranceDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entranceDate);
	}
	
	public void setEntranceDate1(Date entranceDate) throws ParseException {
		this.entranceDate =entranceDate;
	}
	
	public Date getExitDate() {
		return exitDate;
	}

	public void setExitDate(String exitDate) throws ParseException {
		this.exitDate =new SimpleDateFormat(Constants.DATEFORMAT).parse(exitDate);
	}
	public void setExitDate1(Date exitDate){
		this.exitDate =exitDate;
	}

	public int getIsOneTimeExpense() {
		return isOneTimeExpense;
	}

	public void setIsOneTimeExpense(int isOneTimeExpense) {
		this.isOneTimeExpense = isOneTimeExpense;
	}

	public boolean isPaidCompleted() {
		return paidCompleted;
	}

	public void setPaidCompleted(boolean paidCompleted) {
		this.paidCompleted = paidCompleted;
	}

	public boolean getIsLargeCar() {
		return isLargeCar;
	}

	public void setIsLargeCar(boolean isLargeCar) {
		this.isLargeCar = isLargeCar;
	}	
	
    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
}
