package com.park.model;

import java.util.Date;

public class Gongzxrecord2 {
    private Integer id;

    private String carnumber;

    private String cardnumber;

    private Date arrivetime;

    private Integer parkid;

    private String parkname;

    private String stoptype;

    private Double shouldcharge;

    private Double discount;

    private Double realpay;

    private String picturepath;

    private String other;

    private String tradenumber;

    private Date leavetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarnumber() {
        return carnumber;
    }

    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber == null ? null : carnumber.trim();
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber == null ? null : cardnumber.trim();
    }

    public Date getArrivetime() {
        return arrivetime;
    }

    public void setArrivetime(Date arrivetime) {
        this.arrivetime = arrivetime;
    }

    public Integer getParkid() {
        return parkid;
    }

    public void setParkid(Integer parkid) {
        this.parkid = parkid;
    }

    public String getParkname() {
        return parkname;
    }

    public void setParkname(String parkname) {
        this.parkname = parkname == null ? null : parkname.trim();
    }

    public String getStoptype() {
        return stoptype;
    }

    public void setStoptype(String stoptype) {
        this.stoptype = stoptype == null ? null : stoptype.trim();
    }

    public Double getShouldcharge() {
        return shouldcharge;
    }

    public void setShouldcharge(Double shouldcharge) {
        this.shouldcharge = shouldcharge;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getRealpay() {
        return realpay;
    }

    public void setRealpay(Double realpay) {
        this.realpay = realpay;
    }

    public String getPicturepath() {
        return picturepath;
    }

    public void setPicturepath(String picturepath) {
        this.picturepath = picturepath == null ? null : picturepath.trim();
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other == null ? null : other.trim();
    }

    public String getTradenumber() {
        return tradenumber;
    }

    public void setTradenumber(String tradenumber) {
        this.tradenumber = tradenumber == null ? null : tradenumber.trim();
    }

    public Date getLeavetime() {
        return leavetime;
    }

    public void setLeavetime(Date leavetime) {
        this.leavetime = leavetime;
    }
}