package com.park.model;

public enum ChannelType {
	EXIT(0), ENTRANCE(1);
	private int value;
	private ChannelType(int val){
		value = val;
	}

	public int getValue(){
		return value;
	}
	
}
