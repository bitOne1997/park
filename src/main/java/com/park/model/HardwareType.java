package com.park.model;

public enum HardwareType {
	CARPORT(0), CHANNEL(1), LEFTPORTINDICATOR(2); 
	private int value;
	private HardwareType(int val){
		value = val;
	}
	
	public int getValue(){
		return value;
	}
}
