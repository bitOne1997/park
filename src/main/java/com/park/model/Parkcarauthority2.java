package com.park.model;

public class Parkcarauthority2 {
    private Integer id;

    private Integer parkid;

    private Integer mincount;

    private Integer maxcount;

    private Boolean ismincount;

    private Boolean ismaxcount;

    private Integer count;

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

    public Integer getMincount() {
        return mincount;
    }

    public void setMincount(Integer mincount) {
        this.mincount = mincount;
    }

    public Integer getMaxcount() {
        return maxcount;
    }

    public void setMaxcount(Integer maxcount) {
        this.maxcount = maxcount;
    }

    public Boolean getIsmincount() {
        return ismincount;
    }

    public void setIsmincount(Boolean ismincount) {
        this.ismincount = ismincount;
    }

    public Boolean getIsmaxcount() {
        return ismaxcount;
    }

    public void setIsmaxcount(Boolean ismaxcount) {
        this.ismaxcount = ismaxcount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other == null ? null : other.trim();
    }
}