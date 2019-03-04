package com.park.model;


public class ParkDetail {
		
	private int id;
	private String name;
	private int portCount;
	private int channelCount;
	private int streetId=1;
	private String streetName;	
	private int portLeftCount;
	private String feeCriterionName;
	private Integer feeCriterionId;
	private int status;
	private int isFree;
	private int floor;
	private int type;
	private double longitude = -1;
	private double latitude = -1;
	private String alias = "";
	private String position;
	private String mapAddr;
	private String date;
	
	private String contact;
	private String number;
	private String pictureUri;
	private String description;
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getPortCount() {
		return portCount;
	}
	public int getChannelCount() {
		return channelCount;
	}
	public int getPortLeftCount() {
		return portLeftCount;
	}
	public int getStreetId() {
		return streetId;
	}
	public void setStreetId(int streetId) {
		this.streetId = streetId;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public Integer getFeeCriterionId() {
		return feeCriterionId;
	}
	public void setFeeCriterionId(Integer feeCriterionId) {
		this.feeCriterionId = feeCriterionId;
	}
	public int getStatus() {
		return status;
	}
	public int getIsFree() {
		return isFree;
	}
	public int getFloor() {
		return floor;
	}
	public int getType() {
		return type;
	}
	public String getPosition() {
		return position;
	}
	public String getDate() {
		return date;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPortCount(int portCount) {
		this.portCount = portCount;
	}
	public void setChannelCount(int channelCount) {
		this.channelCount = channelCount;
	}
	public void setPortLeftCount(int portLeftCount) {
		this.portLeftCount = portLeftCount;
	}
	public String getFeeCriterionName() {
		return feeCriterionName;
	}
	public void setFeeCriterionName(String feeCriterionName) {
		this.feeCriterionName = feeCriterionName;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	public void setIsFree(int isFree) {
		this.isFree = isFree;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getLongitude() {
		return longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public String getAlias() {
		return alias;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getMapAddr() {
		return mapAddr;
	}
	public void setMapAddr(String mapAddr) {
		this.mapAddr = mapAddr;
	}
	public String getContact() {
		return contact;
	}
	public String getNumber() {
		return number;
	}
	public String getPictureUri() {
		return pictureUri;
	}
	public String getDescription() {
		return description;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public void setPictureUri(String pictureUri) {
		this.pictureUri = pictureUri;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
