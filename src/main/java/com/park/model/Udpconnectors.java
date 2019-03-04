package com.park.model;

import java.util.Date;

public class Udpconnectors {
    private String ip;

    private String port;
    
    private String mac;
    
    private String message;

	private Date lastdate;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port == null ? null : port.trim();
    }

    public Date getLastdate() {
        return lastdate;
    }

    public void setLastdate(Date lastdate) {
        this.lastdate = lastdate;
    }

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
    
    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}