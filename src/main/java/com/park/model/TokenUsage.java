package com.park.model;

import java.util.Date;

public class TokenUsage {
	
	
	private int id;
	private int tokenId;
	private String uri;
	private Date date;
	
	
	public int getId() {
		return id;
	}
	public int getTokenId() {
		return tokenId;
	}
	public String getUri() {
		return uri;
	}
	public Date getDate() {
		return date;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	

}
