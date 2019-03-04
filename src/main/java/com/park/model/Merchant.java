package com.park.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Merchant {
	@JsonIgnore
	private int id;
	private String name;
	private int type;
	private String identity;
	private String address;
	private String description;
	private double longitude;
	private double latitude;
	private String contact;
	private String number;
	@JsonIgnore
	private Date date;
	
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getType() {
		return type;
	}
	public String getIdentity() {
		return identity;
	}
	public String getAddress() {
		return address;
	}
	public String getDescription() {
		return description;
	}
	public double getLongitude() {
		return longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public String getContact() {
		return contact;
	}
	public String getNumber() {
		return number;
	}
	public Date getDate() {
		return date;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
}
