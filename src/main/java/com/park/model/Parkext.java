package com.park.model;

public class Parkext {
    private Integer id;

    private Integer parkid;

    private String video1;

    private String video2;

    private String entranceurl;

    private String exiturl;

    private String notify;

    private Boolean ispush;

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

    public String getVideo1() {
        return video1;
    }

    public void setVideo1(String video1) {
        this.video1 = video1 == null ? null : video1.trim();
    }

    public String getVideo2() {
        return video2;
    }

    public void setVideo2(String video2) {
        this.video2 = video2 == null ? null : video2.trim();
    }

    public String getEntranceurl() {
        return entranceurl;
    }

    public void setEntranceurl(String entranceurl) {
        this.entranceurl = entranceurl == null ? null : entranceurl.trim();
    }

    public String getExiturl() {
        return exiturl;
    }

    public void setExiturl(String exiturl) {
        this.exiturl = exiturl == null ? null : exiturl.trim();
    }

    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify == null ? null : notify.trim();
    }

    public Boolean getIspush() {
        return ispush;
    }

    public void setIspush(Boolean ispush) {
        this.ispush = ispush;
    }
}