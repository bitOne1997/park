package com.park.model;

import java.util.Date;

/**
 * @author zpc
 *
 */
public class ParkNews {
	
	private int id = -1;
	private int parkId;
	private String header;
	private String content;
	private String pictureUri;
	private Date date;
	
	
	public int getId() {
		return id;
	}
	public String getHeader() {
		return header;
	}
	public String getContent() {
		return content;
	}
	public String getPictureUri() {
		return pictureUri;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setPictureUri(String pictureUri) {
		this.pictureUri = pictureUri;
	}
	public int getParkId() {
		return parkId;
	}
	public void setParkId(int parkId) {
		this.parkId = parkId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
