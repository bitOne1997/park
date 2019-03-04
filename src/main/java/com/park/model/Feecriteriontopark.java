package com.park.model;

public class Feecriteriontopark {
    private Integer id;

    private Integer parkid;

    private Integer criterionid;

    private Integer cartype;

    private String other;

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

    public Integer getCriterionid() {
        return criterionid;
    }

    public void setCriterionid(Integer criterionid) {
        this.criterionid = criterionid;
    }

    public Integer getCartype() {
        return cartype;
    }

    public void setCartype(Integer cartype) {
        this.cartype = cartype;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other == null ? null : other.trim();
    }
}