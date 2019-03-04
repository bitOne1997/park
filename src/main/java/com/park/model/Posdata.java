package com.park.model;

import java.math.BigDecimal;
import java.util.Date;

public class Posdata {
    private Long id;

    private String credencesnr;

    private String cardsnr;

    private String cardtype;

    private String sitename;

    private String backbyte;

    private Integer mode;

    private String userid;

    private String possnr;

    private BigDecimal money;

    private BigDecimal giving;

    private BigDecimal realmoney;

    private BigDecimal returnmoney;

    private Date starttime;

    private Date endtime;

    private String sysid;

    private String memo;

    private Boolean isarrearage=false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCredencesnr() {
        return credencesnr;
    }

    public void setCredencesnr(String credencesnr) {
        this.credencesnr = credencesnr == null ? null : credencesnr.trim();
    }

    public String getCardsnr() {
        return cardsnr;
    }

    public void setCardsnr(String cardsnr) {
        this.cardsnr = cardsnr == null ? null : cardsnr.trim();
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype == null ? null : cardtype.trim();
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename == null ? null : sitename.trim();
    }

    public String getBackbyte() {
        return backbyte;
    }

    public void setBackbyte(String backbyte) {
        this.backbyte = backbyte == null ? null : backbyte.trim();
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getPossnr() {
        return possnr;
    }

    public void setPossnr(String possnr) {
        this.possnr = possnr == null ? null : possnr.trim();
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getGiving() {
        return giving;
    }

    public void setGiving(BigDecimal giving) {
        this.giving = giving;
    }

    public BigDecimal getRealmoney() {
        return realmoney;
    }

    public void setRealmoney(BigDecimal realmoney) {
        this.realmoney = realmoney;
    }

    public BigDecimal getReturnmoney() {
        return returnmoney;
    }

    public void setReturnmoney(BigDecimal returnmoney) {
        this.returnmoney = returnmoney;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getSysid() {
        return sysid;
    }

    public void setSysid(String sysid) {
        this.sysid = sysid == null ? null : sysid.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Boolean getIsarrearage() {
        return isarrearage;
    }

    public void setIsarrearage(Boolean isarrearage) {
        this.isarrearage = isarrearage;
    }
}