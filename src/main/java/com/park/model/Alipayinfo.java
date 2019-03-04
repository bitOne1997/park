package com.park.model;

public class Alipayinfo {
    private Integer id;

    private String merchant;

    private String alipayid;

    private String appid;

    private String privatekey;

    private String publickey;

    private String alipublickey;

    private Integer parkid;

    private String outparkkey;

    private String other;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant == null ? null : merchant.trim();
    }

    public String getAlipayid() {
        return alipayid;
    }

    public void setAlipayid(String alipayid) {
        this.alipayid = alipayid == null ? null : alipayid.trim();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getPrivatekey() {
        return privatekey;
    }

    public void setPrivatekey(String privatekey) {
        this.privatekey = privatekey == null ? null : privatekey.trim();
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey == null ? null : publickey.trim();
    }

    public String getAlipublickey() {
        return alipublickey;
    }

    public void setAlipublickey(String alipublickey) {
        this.alipublickey = alipublickey == null ? null : alipublickey.trim();
    }

    public Integer getParkid() {
        return parkid;
    }

    public void setParkid(Integer parkid) {
        this.parkid = parkid;
    }

    public String getOutparkkey() {
        return outparkkey;
    }

    public void setOutparkkey(String outparkkey) {
        this.outparkkey = outparkkey == null ? null : outparkkey.trim();
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other == null ? null : other.trim();
    }
}