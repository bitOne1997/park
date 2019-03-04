package com.park.model;

public class Poschargemac {
    private Integer id;

    private Integer macidenter;

    private Integer poschargeid;

    private Integer macidout;

    private String other="";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMacidenter() {
        return macidenter;
    }

    public void setMacidenter(Integer macidenter) {
        this.macidenter = macidenter;
    }

    public Integer getPoschargeid() {
        return poschargeid;
    }

    public void setPoschargeid(Integer poschargeid) {
        this.poschargeid = poschargeid;
    }

    public Integer getMacidout() {
        return macidout;
    }

    public void setMacidout(Integer macidout) {
        this.macidout = macidout;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other == null ? null : other.trim();
    }
}