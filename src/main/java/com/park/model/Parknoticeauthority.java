package com.park.model;

public class Parknoticeauthority {
    private Integer id;

    private Boolean alipay;

    private Boolean weixin;

    private Integer parkid;

    private Boolean gongshang;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getAlipay() {
        return alipay;
    }

    public void setAlipay(Boolean alipay) {
        this.alipay = alipay;
    }

    public Boolean getWeixin() {
        return weixin;
    }

    public void setWeixin(Boolean weixin) {
        this.weixin = weixin;
    }

    public Integer getParkid() {
        return parkid;
    }

    public void setParkid(Integer parkid) {
        this.parkid = parkid;
    }

    public Boolean getGongshang() {
        return gongshang;
    }

    public void setGongshang(Boolean gongshang) {
        this.gongshang = gongshang;
    }
}