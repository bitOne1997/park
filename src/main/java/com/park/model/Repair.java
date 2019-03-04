package com.park.model;

import java.util.Date;

public class Repair {
    private Integer id;

    private Integer hardwaretype;

    private Integer faulttype;

    private String location;

    private Integer parkid;

    private String submitworker;

    private String solveworker;

    private Date submittime;

    private Integer status;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHardwaretype() {
        return hardwaretype;
    }

    public void setHardwaretype(Integer hardwaretype) {
        this.hardwaretype = hardwaretype;
    }

    public Integer getFaulttype() {
        return faulttype;
    }

    public void setFaulttype(Integer faulttype) {
        this.faulttype = faulttype;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Integer getParkid() {
        return parkid;
    }

    public void setParkid(Integer parkid) {
        this.parkid = parkid;
    }

    public String getSubmitworker() {
        return submitworker;
    }

    public void setSubmitworker(String submitworker) {
        this.submitworker = submitworker == null ? null : submitworker.trim();
    }

    public String getSolveworker() {
        return solveworker;
    }

    public void setSolveworker(String solveworker) {
        this.solveworker = solveworker == null ? null : solveworker.trim();
    }

    public Date getSubmittime() {
        return submittime;
    }

    public void setSubmittime(Date submittime) {
        this.submittime = submittime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}