package com.park.model;

public class Similarcarnumber {
    private Integer id;

    private String similarnumber;

    private String realnumber;

    private Integer parkid;

    private String other;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSimilarnumber() {
        return similarnumber;
    }

    public void setSimilarnumber(String similarnumber) {
        this.similarnumber = similarnumber == null ? null : similarnumber.trim();
    }

    public String getRealnumber() {
        return realnumber;
    }

    public void setRealnumber(String realnumber) {
        this.realnumber = realnumber == null ? null : realnumber.trim();
    }

    public Integer getParkid() {
        return parkid;
    }

    public void setParkid(Integer parkid) {
        this.parkid = parkid;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other == null ? null : other.trim();
    }
}