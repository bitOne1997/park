package com.park.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pos {
	private Integer id;

	private String num;

	private Integer parkid;

	private String type;
	
	private String model;

	private Date lasttime;

	private Date addtime;

	private Integer streetid;

	private String operator;
	
	private String parkName;
	
	private String streetName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num == null ? null : num.trim();
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String ParkName) {
		this.parkName = ParkName == null ? null : ParkName.trim();
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String StreetName) {
		this.streetName = StreetName == null ? null : StreetName.trim();
	}

	public Integer getParkid() {
		return parkid;
	}

	public void setParkid(Integer parkid) {
		this.parkid = parkid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	 public String getModel() {
	        return model;
	    }

	 public void setModel(String model) {
	        this.model = model == null ? null : model.trim();
	    }
	
	public Date getLasttime() {
		return lasttime;
	}

	public void setLasttime(String lasttime) throws ParseException {
		this.lasttime = new SimpleDateFormat(Constants.DATEFORMAT).parse(lasttime);
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) throws ParseException {
		this.addtime = new SimpleDateFormat(Constants.DATEFORMAT).parse(addtime);
	}

	public Integer getStreetid() {
		return streetid;
	}

	public void setStreetid(Integer streetid) {
		this.streetid = streetid;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator == null ? null : operator.trim();
	}
}