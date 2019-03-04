package com.park.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.velocity.app.event.ReferenceInsertionEventHandler.referenceInsertExecutor;

public class Monthuser {
	
    private Integer id;

    private Integer type=0;

    private Integer parkid;

    private String parkname;
    
    private String cardnumber;

    private String owner;

    private String platenumber;

    private String platecolor;

    private String certificatetype="";

    private String certificatenumber="";

    private Date starttime;

    private Date endtime;

    private Float payment;

    private Integer status;
    
    private boolean checked;
    
    public boolean getChecked(){
    	return  checked;
    }
    
    public void setChecked(boolean checked){
    	this.checked=checked;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParkid() {
        return parkid;
    }

    public void setParkid(Integer parkid) {
        this.parkid = parkid;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber == null ? null : cardnumber.trim();
    }

    public String getParkname() {
        return parkname;
    }

    public void setParkname(String parkname) {
        this.parkname = parkname == null ? null : parkname.trim();
    }
    
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    public String getPlatenumber() {
        return platenumber;
    }

    public void setPlatenumber(String platenumber) {
        this.platenumber = platenumber == null ? null : platenumber.trim();
    }

    public String getPlatecolor() {
        return platecolor;
    }

    public void setPlatecolor(String platecolor) {
        this.platecolor = platecolor == null ? null : platecolor.trim();
    }

    public String getCertificatetype() {
        return certificatetype;
    }

    public void setCertificatetype(String certificatetype) {
        this.certificatetype = certificatetype == null ? null : certificatetype.trim();
    }

    public String getCertificatenumber() {
        return certificatenumber;
    }

    public void setCertificatenumber(String certificatenumber) {
        this.certificatenumber = certificatenumber == null ? null : certificatenumber.trim();
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) throws ParseException {
        this.starttime = new SimpleDateFormat(Constants.DATEFORMAT).parse(starttime);
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) throws ParseException {
        this.endtime = new SimpleDateFormat(Constants.DATEFORMAT).parse(endtime);
    }
    public void setEndtime1(Date endtime){
    	this.endtime=endtime;
    }
    public Float getPayment() {
        return payment;
    }

    public void setPayment(Float payment) {
        this.payment = payment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}