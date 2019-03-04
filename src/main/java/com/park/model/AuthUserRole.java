package com.park.model;

public enum AuthUserRole {
	ADMIN(0), USER(1);
	
	private int role;
	private AuthUserRole(int r){
		role = r;
	}
	
	public int getValue(){
		return role;
	}
	

}
