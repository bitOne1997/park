package com.park.model;

import java.util.Date;

public class Alipayrecord {
    private Integer id;

    private String outTradeNo;

    private Integer poschargeid;

    private String status;

    private Double money;

    private String alitradeno;

    private Date date;

    private String userid;

    private String parkingid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
    }

    public Integer getPoschargeid() {
        return poschargeid;
    }

    public void setPoschargeid(Integer poschargeid) {
        this.poschargeid = poschargeid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getAlitradeno() {
        return alitradeno;
    }

    public void setAlitradeno(String alitradeno) {
        this.alitradeno = alitradeno == null ? null : alitradeno.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getParkingid() {
        return parkingid;
    }

    public void setParkingid(String parkingid) {
        this.parkingid = parkingid == null ? null : parkingid.trim();
    }
}