package com.park.model;


public class QueueDataCharge {
	int id;

	String plateNumber = "";

	int parkId = 4;

	String parkDesc = "";

//	String portNumber = "0";

	String rejectReason = "";
	// 0是支付宝 1是微信 2是现金
	int payType = 2;

	String actionType = "in";

	String operatorId = "operator";

	String posId = "posid";
	
	int parkingType=9;

	double chargeMoney = 0;

	double paidMoney = 0.0;

//	double unPaidMoney = 0.0;

//	double givenMoney = 0.0;

//	double changeMoney = 0.0;

//	int isOneTimeExpense = 0;

//	boolean paidCompleted = false;

	int isLargeCar = 0;

	String entranceDate;

	String exitDate;

	String url = "";

	Double discount = 0.0;

//	Byte discountType = 0;

//	Double other = 0.0;

//	String other2 = "";

	String outUrl = "";

	int leftCount = 0;

	String mac = "";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public int getParkingType() {
		return parkingType;
	}

	public void setParkingType(int parkingType) {
		this.parkingType = parkingType;
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

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
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

	public int getIsLargeCar() {
		return isLargeCar;
	}

	public void setIsLargeCar(int isLargeCar) {
		this.isLargeCar = isLargeCar;
	}

	public String getEntranceDate() {
		return entranceDate;
	}

	public void setEntranceDate(String entranceDate) {
		this.entranceDate = entranceDate;
	}

	public String getExitDate() {
		return exitDate;
	}

	public void setExitDate(String exitDate) {
		this.exitDate = exitDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getOutUrl() {
		return outUrl;
	}

	public void setOutUrl(String outUrl) {
		this.outUrl = outUrl;
	}

	public int getLeftCount() {
		return leftCount;
	}

	public void setLeftCount(int leftCount) {
		this.leftCount = leftCount;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}


	
}
