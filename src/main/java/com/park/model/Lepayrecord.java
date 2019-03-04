package com.park.model;

import java.util.Date;

public class Lepayrecord {
    private Integer id;

    private Short paytype;

    private String mchid;

    private String cmpappid;

    private Integer amount;

    private String outtradeno;

    private String paytypetradeno;

    private String orderno;

    private Date date;

    private Integer poschargeid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getPaytype() {
        return paytype;
    }

    public void setPaytype(Short paytype) {
        this.paytype = paytype;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid == null ? null : mchid.trim();
    }

    public String getCmpappid() {
        return cmpappid;
    }

    public void setCmpappid(String cmpappid) {
        this.cmpappid = cmpappid == null ? null : cmpappid.trim();
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getOuttradeno() {
        return outtradeno;
    }

    public void setOuttradeno(String outtradeno) {
        this.outtradeno = outtradeno == null ? null : outtradeno.trim();
    }

    public String getPaytypetradeno() {
        return paytypetradeno;
    }

    public void setPaytypetradeno(String paytypetradeno) {
        this.paytypetradeno = paytypetradeno == null ? null : paytypetradeno.trim();
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPoschargeid() {
        return poschargeid;
    }

    public void setPoschargeid(Integer poschargeid) {
        this.poschargeid = poschargeid;
    }
}