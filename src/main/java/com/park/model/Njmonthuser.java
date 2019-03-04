package com.park.model;

import java.util.Date;

public class Njmonthuser {
    private Integer id;

    private Date tradedate;

    private String monthid;

    private String monthtype;

    private String cardnumber;

    private String carnumber;

    private String cartype;

    private String membername;

    private String effectivetimes;

    private String tradenumber;

    private Date monthstart;

    private Date monthend;

    private Integer rechargebefore;

    private Integer rechargeafter;

    private Integer rechargemoney;

    private Integer monthfee;

    private Integer discount;

    private Integer standardfees;

    private Integer shouldcharge;

    private Integer realpay;

    private Integer preferential;

    private String picturepath;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTradedate() {
        return tradedate;
    }

    public void setTradedate(Date tradedate) {
        this.tradedate = tradedate;
    }

    public String getMonthid() {
        return monthid;
    }

    public void setMonthid(String monthid) {
        this.monthid = monthid == null ? null : monthid.trim();
    }

    public String getMonthtype() {
        return monthtype;
    }

    public void setMonthtype(String monthtype) {
        this.monthtype = monthtype == null ? null : monthtype.trim();
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber == null ? null : cardnumber.trim();
    }

    public String getCarnumber() {
        return carnumber;
    }

    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber == null ? null : carnumber.trim();
    }

    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype == null ? null : cartype.trim();
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername == null ? null : membername.trim();
    }

    public String getEffectivetimes() {
        return effectivetimes;
    }

    public void setEffectivetimes(String effectivetimes) {
        this.effectivetimes = effectivetimes == null ? null : effectivetimes.trim();
    }

    public String getTradenumber() {
        return tradenumber;
    }

    public void setTradenumber(String tradenumber) {
        this.tradenumber = tradenumber == null ? null : tradenumber.trim();
    }

    public Date getMonthstart() {
        return monthstart;
    }

    public void setMonthstart(Date monthstart) {
        this.monthstart = monthstart;
    }

    public Date getMonthend() {
        return monthend;
    }

    public void setMonthend(Date monthend) {
        this.monthend = monthend;
    }

    public Integer getRechargebefore() {
        return rechargebefore;
    }

    public void setRechargebefore(Integer rechargebefore) {
        this.rechargebefore = rechargebefore;
    }

    public Integer getRechargeafter() {
        return rechargeafter;
    }

    public void setRechargeafter(Integer rechargeafter) {
        this.rechargeafter = rechargeafter;
    }

    public Integer getRechargemoney() {
        return rechargemoney;
    }

    public void setRechargemoney(Integer rechargemoney) {
        this.rechargemoney = rechargemoney;
    }

    public Integer getMonthfee() {
        return monthfee;
    }

    public void setMonthfee(Integer monthfee) {
        this.monthfee = monthfee;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getStandardfees() {
        return standardfees;
    }

    public void setStandardfees(Integer standardfees) {
        this.standardfees = standardfees;
    }

    public Integer getShouldcharge() {
        return shouldcharge;
    }

    public void setShouldcharge(Integer shouldcharge) {
        this.shouldcharge = shouldcharge;
    }

    public Integer getRealpay() {
        return realpay;
    }

    public void setRealpay(Integer realpay) {
        this.realpay = realpay;
    }

    public Integer getPreferential() {
        return preferential;
    }

    public void setPreferential(Integer preferential) {
        this.preferential = preferential;
    }

    public String getPicturepath() {
        return picturepath;
    }

    public void setPicturepath(String picturepath) {
        this.picturepath = picturepath == null ? null : picturepath.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}