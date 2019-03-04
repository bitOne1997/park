package com.park.model;

public class Hongxingrecord {
    private Integer id;

    private String parkkey;

    private String carno;

    private String carlock;

    private String orderno;

    private String cartype;

    private String entertime;

    private String entergatename;

    private String enteroperatorname;

    private String enterimg;

    private Integer freetime;

    private Integer freetimeout;

    private Float totalamount;

    private Float couponamount;

    private Float payamount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParkkey() {
        return parkkey;
    }

    public void setParkkey(String parkkey) {
        this.parkkey = parkkey == null ? null : parkkey.trim();
    }

    public String getCarno() {
        return carno;
    }

    public void setCarno(String carno) {
        this.carno = carno == null ? null : carno.trim();
    }

    public String getCarlock() {
        return carlock;
    }

    public void setCarlock(String carlock) {
        this.carlock = carlock == null ? null : carlock.trim();
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype == null ? null : cartype.trim();
    }

    public String getEntertime() {
        return entertime;
    }

    public void setEntertime(String entertime) {
        this.entertime = entertime == null ? null : entertime.trim();
    }

    public String getEntergatename() {
        return entergatename;
    }

    public void setEntergatename(String entergatename) {
        this.entergatename = entergatename == null ? null : entergatename.trim();
    }

    public String getEnteroperatorname() {
        return enteroperatorname;
    }

    public void setEnteroperatorname(String enteroperatorname) {
        this.enteroperatorname = enteroperatorname == null ? null : enteroperatorname.trim();
    }

    public String getEnterimg() {
        return enterimg;
    }

    public void setEnterimg(String enterimg) {
        this.enterimg = enterimg == null ? null : enterimg.trim();
    }

    public Integer getFreetime() {
        return freetime;
    }

    public void setFreetime(Integer freetime) {
        this.freetime = freetime;
    }

    public Integer getFreetimeout() {
        return freetimeout;
    }

    public void setFreetimeout(Integer freetimeout) {
        this.freetimeout = freetimeout;
    }

    public Float getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(Float totalamount) {
        this.totalamount = totalamount;
    }

    public Float getCouponamount() {
        return couponamount;
    }

    public void setCouponamount(Float couponamount) {
        this.couponamount = couponamount;
    }

    public Float getPayamount() {
        return payamount;
    }

    public void setPayamount(Float payamount) {
        this.payamount = payamount;
    }
}