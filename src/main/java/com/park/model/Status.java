package com.park.model;

public enum Status {
	USED(0), UNUSED(1);
	
	private int value;
	
	private Status(int val){
		value = val;
	}
	public int getValue(){
		return value;
	}
}
