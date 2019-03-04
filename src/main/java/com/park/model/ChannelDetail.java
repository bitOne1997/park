package com.park.model;

public class ChannelDetail {
	
	private int id;
	private int parkId;
	private String parkName;
	private int channelId;
	private Integer macId;
	private String mac;
	private String channelFlag;
	private String status;
	private String description;
	private String date;
	
	
	public int getId() {
		return id;
	}

	public String getParkName() {
		return parkName;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getMac() {
		return mac;
	}
	public String getChannelFlag() {
		return channelFlag;
	}
	public String getStatus() {
		return status;
	}
	public String getDescription() {
		return description;
	}
	public String getDate() {
		return date;
	}
	public void setId(int id) {
		this.id = id;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
	public void setChannelFlag(String channelFlag) {
		this.channelFlag = channelFlag;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public int getParkId() {
		return parkId;
	}

	public Integer getMacId() {
		return macId;
	}

	public void setParkId(int parkId) {
		this.parkId = parkId;
	}

	public void setMacId(Integer macId) {
		this.macId = macId;
	}
	
	

}
