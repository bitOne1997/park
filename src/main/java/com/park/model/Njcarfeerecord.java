package com.park.model;

import java.util.Date;

public class Njcarfeerecord {
    private Integer id;

    private Date arrivetime;

    private Date leavetime;

    private Date tradedate;

    private String carnumber;

    private String tradenumber;

    private String stoptype;

    private String parkname;

    private Integer parkid;

    private Integer shouldcharge;

    private Integer otherpay;

    private Integer discount;

    private Integer realpay;

    private Integer arrearspay;

    private Integer paidmoney;

    private String picturepath;

    private String invoiceurl;

    private String cartype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getArrivetime() {
        return arrivetime;
    }

    public void setArrivetime(Date arrivetime) {
        this.arrivetime = arrivetime;
    }

    public Date getLeavetime() {
        return leavetime;
    }

    public void setLeavetime(Date leavetime) {
        this.leavetime = leavetime;
    }

    public Date getTradedate() {
        return tradedate;
    }

    public void setTradedate(Date tradedate) {
        this.tradedate = tradedate;
    }

    public String getCarnumber() {
        return carnumber;
    }

    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber == null ? null : carnumber.trim();
    }

    public String getTradenumber() {
        return tradenumber;
    }

    public void setTradenumber(String tradenumber) {
        this.tradenumber = tradenumber == null ? null : tradenumber.trim();
    }

    public String getStoptype() {
        return stoptype;
    }

    public void setStoptype(String stoptype) {
        this.stoptype = stoptype == null ? null : stoptype.trim();
    }

    public String getParkname() {
        return parkname;
    }

    public void setParkname(String parkname) {
        this.parkname = parkname == null ? null : parkname.trim();
    }

    public Integer getParkid() {
        return parkid;
    }

    public void setParkid(Integer parkid) {
        this.parkid = parkid;
    }

    public Integer getShouldcharge() {
        return shouldcharge;
    }

    public void setShouldcharge(Integer shouldcharge) {
        this.shouldcharge = shouldcharge;
    }

    public Integer getOtherpay() {
        return otherpay;
    }

    public void setOtherpay(Integer otherpay) {
        this.otherpay = otherpay;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getRealpay() {
        return realpay;
    }

    public void setRealpay(Integer realpay) {
        this.realpay = realpay;
    }

    public Integer getArrearspay() {
        return arrearspay;
    }

    public void setArrearspay(Integer arrearspay) {
        this.arrearspay = arrearspay;
    }

    public Integer getPaidmoney() {
        return paidmoney;
    }

    public void setPaidmoney(Integer paidmoney) {
        this.paidmoney = paidmoney;
    }

    public String getPicturepath() {
        return picturepath;
    }

    public void setPicturepath(String picturepath) {
        this.picturepath = picturepath == null ? null : picturepath.trim();
    }

    public String getInvoiceurl() {
        return invoiceurl;
    }

    public void setInvoiceurl(String invoiceurl) {
        this.invoiceurl = invoiceurl == null ? null : invoiceurl.trim();
    }

    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype == null ? null : cartype.trim();
    }
}