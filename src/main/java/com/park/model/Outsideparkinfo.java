package com.park.model;

import java.util.Date;

public class Outsideparkinfo {
    private Integer id;

    private Float amountmoney=0f;

    private Float realmoney=0f;
    
    private Float arrearage=0f;

    private Integer entrancecount=0;

    private Integer outcount=0;

    private Integer carportcount;

    private Integer unusedcarportcount=0;

    private Integer parkid;

    private Date possigndate;

    private Float poslng;

    private Float poslat;

    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getAmountmoney() {
        return amountmoney;
    }

    public void setAmountmoney(Float amountmoney) {
        this.amountmoney = amountmoney;
    }

    public Float getRealmoney() {
        return realmoney;
    }

    public void setRealmoney(Float realmoney) {
        this.realmoney = realmoney;
    }
    

    public Float getArrearage() {
        return arrearage;
    }

    public void setArrearage(Float arrearage) {
        this.arrearage = arrearage;
    }

    public Integer getEntrancecount() {
        return entrancecount;
    }

    public void setEntrancecount(Integer entrancecount) {
        this.entrancecount = entrancecount;
    }

    public Integer getOutcount() {
        return outcount;
    }

    public void setOutcount(Integer outcount) {
        this.outcount = outcount;
    }

    public Integer getCarportcount() {
        return carportcount;
    }

    public void setCarportcount(Integer carportcount) {
        this.carportcount = carportcount;
    }

    public Integer getUnusedcarportcount() {
        return unusedcarportcount;
    }

    public void setUnusedcarportcount(Integer unusedcarportcount) {
        this.unusedcarportcount = unusedcarportcount;
    }

    public Integer getParkid() {
        return parkid;
    }

    public void setParkid(Integer parkid) {
        this.parkid = parkid;
    }

    public Date getPossigndate() {
        return possigndate;
    }

    public void setPossigndate(Date possigndate) {
        this.possigndate = possigndate;
    }

    public Float getPoslng() {
        return poslng;
    }

    public void setPoslng(Float poslng) {
        this.poslng = poslng;
    }

    public Float getPoslat() {
        return poslat;
    }

    public void setPoslat(Float poslat) {
        this.poslat = poslat;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}