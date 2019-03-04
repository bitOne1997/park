package com.park.model;

import org.codehaus.jackson.annotate.JsonIgnore;

public class AuthUser {

	@JsonIgnore
	private int id;
	private String username;
	private String password;
	private int role;
	
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public int getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setUsername(String userName) {
		this.username = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
