package com.park.model;

public class UserTokenUsage {
	
	private int userId;
	private String username;
	private String companyInfo;
	private int tokenId;
	private String uri;
	private int count;
	
	public int getUserId() {
		return userId;
	}
	public String getUsername() {
		return username;
	}
	public String getCompanyInfo() {
		return companyInfo;
	}
	public int getTokenId() {
		return tokenId;
	}
	public String getUri() {
		return uri;
	}
	public int getCount() {
		return count;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setCompanyInfo(String companyInfo) {
		this.companyInfo = companyInfo;
	}
	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
	

}
