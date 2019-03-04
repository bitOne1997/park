package com.park.model;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Carport {
	
	@JsonIgnore
	private int Id;
	private String name;
	private int isout;
	private String province;
	private String city;
	private String district;
	private String street;
	private double longitude;
	private double latitude;
	private int uid;
	@JsonIgnore
	private int parkId = -1;
	private double price;
	private String unit = "M";
	private String tenancy;
	private String contact;
	private String number;
	private String description;
	private Date date;
	
	
	
	public int getId() {
		return Id;
	}



	public String getName() {
		return name;
	}



	public String getProvince() {
		return province;
	}



	public String getCity() {
		return city;
	}



	public String getDistrict() {
		return district;
	}



	public String getStreet() {
		return street;
	}



	public double getLongitude() {
		return longitude;
	}



	public double getLatitude() {
		return latitude;
	}



	public int getUid() {
		return uid;
	}



	public int getParkId() {
		return parkId;
	}



	public double getPrice() {
		return price;
	}



	public String getUnit() {
		return unit;
	}



	public String getTenancy() {
		return tenancy;
	}



	public String getContact() {
		return contact;
	}



	public String getNumber() {
		return number;
	}



	public String getDescription() {
		return description;
	}



	public Date getDate() {
		return date;
	}

	public int isIsout() {
		return isout;
	}



	public void setIsout(int isout) {
		this.isout = isout;
	}


	public void setId(int id) {
		Id = id;
	}



	public void setName(String name) {
		this.name = name;
	}



	public void setProvince(String province) {
		this.province = province;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public void setDistrict(String district) {
		this.district = district;
	}



	public void setStreet(String street) {
		this.street = street;
	}



	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}



	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}



	public void setUid(int uid) {
		this.uid = uid;
	}



	public void setParkId(int parkId) {
		this.parkId = parkId;
	}



	public void setPrice(double price) {
		this.price = price;
	}



	public void setUnit(String unit) {
		this.unit = unit;
	}



	public void setTenancy(String tenancy) {
		this.tenancy = tenancy;
	}



	public void setContact(String contact) {
		this.contact = contact;
	}



	public void setNumber(String number) {
		this.number = number;
	}



	public void setDescription(String description) {
		this.description = description;
	}


//
//	public void setDate(Date date) {
		
//		this.date = date;
//	}



	public void setDate(String date) {
		try {
			this.date = new SimpleDateFormat(Constants.DATEFORMAT).parse(date);
		} catch (ParseException e) {		
			e.printStackTrace();
		}
	}
	
	

	

}
