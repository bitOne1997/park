package com.park.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hardwareinfo {
    private Integer id;

    private String parkname;

    private Integer parkid;

    private String type;

    private String mac;

    private String simnumber;

    private String simstatus;

    private Float flow;

    private String macversion;

    private String softversion;

    private Float signalstatus;

    private String macstatus;

    private Date heartstatus;

    private Float temperature;

    private Date installtime;

    private String other;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParkname() {
        return parkname;
    }

    public void setParkname(String parkname) {
        this.parkname = parkname == null ? null : parkname.trim();
    }

    public Integer getParkid() {
        return parkid;
    }

    public void setParkid(Integer parkid) {
        this.parkid = parkid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac == null ? null : mac.trim();
    }

    public String getSimnumber() {
        return simnumber;
    }

    public void setSimnumber(String simnumber) {
        this.simnumber = simnumber == null ? null : simnumber.trim();
    }

    public String getSimstatus() {
        return simstatus;
    }

    public void setSimstatus(String simstatus) {
        this.simstatus = simstatus == null ? null : simstatus.trim();
    }

    public Float getFlow() {
        return flow;
    }

    public void setFlow(Float flow) {
        this.flow = flow;
    }

    public String getMacversion() {
        return macversion;
    }

    public void setMacversion(String macversion) {
        this.macversion = macversion == null ? null : macversion.trim();
    }

    public String getSoftversion() {
        return softversion;
    }

    public void setSoftversion(String softversion) {
        this.softversion = softversion == null ? null : softversion.trim();
    }

    public Float getSignalstatus() {
        return signalstatus;
    }

    public void setSignalstatus(Float signalstatus) {
        this.signalstatus = signalstatus;
    }

    public String getMacstatus() {
        return macstatus;
    }

    public void setMacstatus(String macstatus) {
        this.macstatus = macstatus == null ? null : macstatus.trim();
    }

    public Date getHeartstatus() {
        return heartstatus;
    }

    public void setHeartstatus(Date heartstatus) {
        this.heartstatus = heartstatus;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Date getInstalltime() {
        return installtime;
    }

    public void setInstalltime(String installtime) throws ParseException {
        this.installtime = new SimpleDateFormat(Constants.DATEFORMAT).parse(installtime);
    }
    
    public void setInstalltime1(Date installtime) {
        this.installtime = installtime;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other == null ? null : other.trim();
    }
}