package com.park.model;

public class Parktoalipark {
    private Integer id;

    private Integer parkid;

    private String aliparkingid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParkid() {
        return parkid;
    }

    public void setParkid(Integer parkid) {
        this.parkid = parkid;
    }

    public String getAliparkingid() {
        return aliparkingid;
    }

    public void setAliparkingid(String aliparkingid) {
        this.aliparkingid = aliparkingid == null ? null : aliparkingid.trim();
    }
}