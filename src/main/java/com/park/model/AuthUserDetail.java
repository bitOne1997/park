package com.park.model;

import java.util.List;

public class AuthUserDetail {
	
	private int id;
	private String username;
	private int role;
	
	private List<String> parkName;

	public AuthUserDetail(){}
	
	public AuthUserDetail(AuthUser user, List<String> parkName){
		id = user.getId();
		username = user.getUsername();
		role = user.getRole();
		this.parkName = parkName;
		
	}
	
	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}


	public int getRole() {
		return role;
	}

	public List<String> getParkName() {
		return parkName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public void setRole(int role) {
		this.role = role;
	}

	public void setParkName(List<String> parkName) {
		this.parkName = parkName;
	}
	
	
	

}
