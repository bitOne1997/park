package com.park.model;

import java.util.Date;

public class Token {

	private int id;
	private String token;
	private String description;
	private Date expire;
	
	public int getId() {
		return id;
	}
	public String getToken() {
		return token;
	}
	public String getDescription() {
		return description;
	}
	public Date getExpire() {
		return expire;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setExpire(Date expire) {
		this.expire = expire;
	}
	
	
	
}
