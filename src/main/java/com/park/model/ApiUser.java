package com.park.model;

public class ApiUser {
	
	private int id;
	private String username;
	private String companyInfo;
	private String contact;
	private String number;
	private int tokenId;
	
	
	public int getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getCompanyInfo() {
		return companyInfo;
	}
	public String getContact() {
		return contact;
	}
	public String getNumber() {
		return number;
	}
	public int getTokenId() {
		return tokenId;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setCompanyInfo(String companyInfo) {
		this.companyInfo = companyInfo;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}
	
	

}
