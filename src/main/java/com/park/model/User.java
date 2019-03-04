package com.park.model;

import org.codehaus.jackson.annotate.JsonIgnore;

public class User {

	@JsonIgnore
	private int Id;
	private String userName;
	private String number;
	private String passwd;
	private String headerUri;
	private String nickname;
	private Float balance;
	private Byte credit;
	public Float getBalance() {
		return balance;
	}
	public void setBalance(Float balance) {
		this.balance = balance;
	}
	public Byte getCredit() {
		return credit;
	}
	public void setCredit(Byte credit) {
		this.credit = credit;
	}
	public String getUserName() {
		return userName;
	}
	public String getNumber() {
		return number;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getHeaderUri() {
		return headerUri;
	}
	public String getNickname() {
		return nickname;
	}
	public void setHeaderUri(String headerUri) {
		this.headerUri = headerUri;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
	
}
