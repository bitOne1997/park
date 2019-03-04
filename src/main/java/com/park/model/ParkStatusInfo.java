package com.park.model;

import java.util.HashMap;
import java.util.Map;

public class ParkStatusInfo {


	int onlineCount=0;
	int totalCount=0;
	Map<String, Integer> typeCount=new HashMap<>();
	Map<String, Integer> online=new HashMap<>();
	
	public int getOnlineCount() {
		return onlineCount;
	}
	public void setOnlineCount(int onlineCount) {
		this.onlineCount = onlineCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public Map<String, Integer> getTypeCount() {
		return typeCount;
	}
	public void setTypeCount(Map<String, Integer> typeCount) {
		this.typeCount = typeCount;
	}
	public Map<String, Integer> getOnline() {
		return online;
	}
	public void setOnline(Map<String, Integer> online) {
		this.online = online;
	}
}
