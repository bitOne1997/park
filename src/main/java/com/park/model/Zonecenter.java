package com.park.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Zonecenter {
    private Integer id;

    private String num;

    private String name;

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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num == null ? null : num.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
        this.date = new SimpleDateFormat(Constants.DATEFORMAT).parse(date);
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other == null ? null : other.trim();
    }
    
    @Override
    public boolean equals(Object obj){
    	Zonecenter zonecenter=(Zonecenter) obj;
    	return id==zonecenter.id;
    }
    @Override  
    public int hashCode() {  
    String in = id + name;  
    return in.hashCode();  
    }  
}