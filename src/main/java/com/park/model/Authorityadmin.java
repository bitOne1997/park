package com.park.model;

public class Authorityadmin {
    private Integer id;

    private String sign;

    private String token;

    private String explain;

    private Integer maxparknumber;

    private Integer maxusernumber;

    private String other;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain == null ? null : explain.trim();
    }

    public Integer getMaxparknumber() {
        return maxparknumber;
    }

    public void setMaxparknumber(Integer maxparknumber) {
        this.maxparknumber = maxparknumber;
    }

    public Integer getMaxusernumber() {
        return maxusernumber;
    }

    public void setMaxusernumber(Integer maxusernumber) {
        this.maxusernumber = maxusernumber;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other == null ? null : other.trim();
    }
}