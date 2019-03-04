package com.park.model;

import java.util.Date;

public class PoschargedataNew {
    private Integer id;

    private String cardnumber="不识别";

    private Integer parkid;

    private String parkdesc="默认";

    private String portnumber="默认";

    private Boolean isentrance=true;

    private String operatorid;

    private String posid;

    private Double chargemoney=0.0;

    private Double paidmoney=0.0;

    private Double unpaidmoney=0.0;

    private Double givenmoney=0.0;

    private Double changemoney=0.0;

    private Boolean isonetimeexpense;

    private Boolean paidcompleted=false;

    private Boolean islargecar=false;

    private Date entrancedate;

    private Date exitdate;

    private String url="";

    private String rejectreason;

    private Byte paytype=2;

    private Double discount=0.0;

    private Byte discounttype=0;

    private Double other;

    private String other2;
    
    private String outurl;

    public String getOuturl() {
		return outurl;
	}

	public void setOuturl(String outurl) {
		this.outurl = outurl;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber == null ? null : cardnumber.trim();
    }

    public Integer getParkid() {
        return parkid;
    }

    public void setParkid(Integer parkid) {
        this.parkid = parkid;
    }

    public String getParkdesc() {
        return parkdesc;
    }

    public void setParkdesc(String parkdesc) {
        this.parkdesc = parkdesc == null ? null : parkdesc.trim();
    }

    public String getPortnumber() {
        return portnumber;
    }

    public void setPortnumber(String portnumber) {
        this.portnumber = portnumber == null ? null : portnumber.trim();
    }

    public Boolean getIsentrance() {
        return isentrance;
    }

    public void setIsentrance(Boolean isentrance) {
        this.isentrance = isentrance;
    }

    public String getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid == null ? null : operatorid.trim();
    }

    public String getPosid() {
        return posid;
    }

    public void setPosid(String posid) {
        this.posid = posid == null ? null : posid.trim();
    }

    public Double getChargemoney() {
        return chargemoney;
    }

    public void setChargemoney(Double chargemoney) {
        this.chargemoney = chargemoney;
    }

    public Double getPaidmoney() {
        return paidmoney;
    }

    public void setPaidmoney(Double paidmoney) {
        this.paidmoney = paidmoney;
    }

    public Double getUnpaidmoney() {
        return unpaidmoney;
    }

    public void setUnpaidmoney(Double unpaidmoney) {
        this.unpaidmoney = unpaidmoney;
    }

    public Double getGivenmoney() {
        return givenmoney;
    }

    public void setGivenmoney(Double givenmoney) {
        this.givenmoney = givenmoney;
    }

    public Double getChangemoney() {
        return changemoney;
    }

    public void setChangemoney(Double changemoney) {
        this.changemoney = changemoney;
    }

    public Boolean getIsonetimeexpense() {
        return isonetimeexpense;
    }

    public void setIsonetimeexpense(Boolean isonetimeexpense) {
        this.isonetimeexpense = isonetimeexpense;
    }

    public Boolean getPaidcompleted() {
        return paidcompleted;
    }

    public void setPaidcompleted(Boolean paidcompleted) {
        this.paidcompleted = paidcompleted;
    }

    public Boolean getIslargecar() {
        return islargecar;
    }

    public void setIslargecar(Boolean islargecar) {
        this.islargecar = islargecar;
    }

    public Date getEntrancedate() {
        return entrancedate;
    }

    public void setEntrancedate(Date entrancedate) {
        this.entrancedate = entrancedate;
    }

    public Date getExitdate() {
        return exitdate;
    }

    public void setExitdate(Date exitdate) {
        this.exitdate = exitdate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getRejectreason() {
        return rejectreason;
    }

    public void setRejectreason(String rejectreason) {
        this.rejectreason = rejectreason == null ? null : rejectreason.trim();
    }

    public Byte getPaytype() {
        return paytype;
    }

    public void setPaytype(Byte paytype) {
        this.paytype = paytype;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Byte getDiscounttype() {
        return discounttype;
    }

    public void setDiscounttype(Byte discounttype) {
        this.discounttype = discounttype;
    }

    public Double getOther() {
        return other;
    }

    public void setOther(Double other) {
        this.other = other;
    }

    public String getOther2() {
        return other2;
    }

    public void setOther2(String other2) {
        this.other2 = other2 == null ? null : other2.trim();
    }
}