package com.park.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Street {
    private Integer id;

    private String number;

    private String name;

    private Integer areaid;

    private String areaName;
    
    private Integer type;

    private String contact;

    private String phone;

    private Date date;

    private String other;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAreaName() {
        return areaName;
    }
    public void setAreaName(String AreaName) {
        this.areaName = AreaName == null ? null : AreaName.trim();
    }
    
    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(String date) throws ParseException {
        this.date = new  SimpleDateFormat(Constants.DATEFORMAT).parse(date);
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other == null ? null : other.trim();
    }
    
    @Override
    public boolean equals(Object obj){
    	Street street=(Street)obj;
    	return id==street.getId();
    }
    @Override  
    public int hashCode() {  
    String in = id + name;  
    return in.hashCode();  
    }  
}