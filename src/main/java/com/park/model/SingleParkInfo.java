package com.park.model;

public class SingleParkInfo {
	 String ParkId;
	 String TotRemainNum;
	 String MonthlyRemainNum;
	 String GuesRemainNum;
	 String Updatetime;
	 String Sendtime;
	
//public SingleParkInfo(String ParkId,String TotRemainNum,String MonthlyRemainNum,String GuesRemainNum, String Updatetime, String Sendtime){
//		this.ParkId=ParkId;
//		this.TotRemainNum=TotRemainNum;
//		this.MonthlyRemainNum=MonthlyRemainNum;
//		this.GuesRemainNum=GuesRemainNum;
//		this.Updatetime=Updatetime;
//		this.Sendtime=Sendtime;
//}
//public SingleParkInfo(){
////	this.ParkId="test";
//}
public String toString(){
	return "ood";
}

public String getParkId() {
	return ParkId;
}

public void setParkId(String parkId) {
	ParkId = parkId;
}

public String getTotRemainNum() {
	return TotRemainNum;
}

public void setTotRemainNum(String totRemainNum) {
	TotRemainNum = totRemainNum;
}

public String getMonthlyRemainNum() {
	return MonthlyRemainNum;
}

public void setMonthlyRemainNum(String monthlyRemainNum) {
	MonthlyRemainNum = monthlyRemainNum;
}

public String getGuesRemainNum() {
	return GuesRemainNum;
}

public void setGuesRemainNum(String guesRemainNum) {
	GuesRemainNum = guesRemainNum;
}

public String getUpdatetime() {
	return Updatetime;
}

public void setUpdatetime(String updatetime) {
	Updatetime = updatetime;
}

public String getSendtime() {
	return Sendtime;
}

public void setSendtime(String sendtime) {
	Sendtime = sendtime;
}
}