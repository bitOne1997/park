package com.park.model;

import java.util.Date;

public class ChargedataParkWithTable {
    private Integer id;

    private String carnumber;

    private Integer parkid;

    private String parkdesc;

    private String portnumber;

    private String operatorid;

    private String posid;

    private Integer chargemoney;

    private Integer paidmoney;

    private Integer unpaidmoney;

    private Integer givenmoney;

    private Integer changemoney;

    private Boolean isonetimeexpense;

    private Boolean paidcompleted;

    private Boolean islargecar;

    private Date entrancedate;

    private Date exitdate;

    private String inpictureurl;

    private String outpirctureurl;

    private String rejectreason;

    private Byte paytype;

    private Integer discount;

    private String discounttype;
    
    private String tableName;

	private Integer reserve1;

    private String reserve2;

    private Integer other;

    private String other2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarnumber() {
        return carnumber;
    }

    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber == null ? null : carnumber.trim();
    }

    public Integer getParkid() {
        return parkid;
    }

    public void setParkid(Integer parkid) {
        this.parkid = parkid;
    }

    public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
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

    public Integer getChargemoney() {
        return chargemoney;
    }

    public void setChargemoney(Integer chargemoney) {
        this.chargemoney = chargemoney;
    }

    public Integer getPaidmoney() {
        return paidmoney;
    }

    public void setPaidmoney(Integer paidmoney) {
        this.paidmoney = paidmoney;
    }

    public Integer getUnpaidmoney() {
        return unpaidmoney;
    }

    public void setUnpaidmoney(Integer unpaidmoney) {
        this.unpaidmoney = unpaidmoney;
    }

    public Integer getGivenmoney() {
        return givenmoney;
    }

    public void setGivenmoney(Integer givenmoney) {
        this.givenmoney = givenmoney;
    }

    public Integer getChangemoney() {
        return changemoney;
    }

    public void setChangemoney(Integer changemoney) {
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

    public String getInpictureurl() {
        return inpictureurl;
    }

    public void setInpictureurl(String inpictureurl) {
        this.inpictureurl = inpictureurl == null ? null : inpictureurl.trim();
    }

    public String getOutpirctureurl() {
        return outpirctureurl;
    }

    public void setOutpirctureurl(String outpirctureurl) {
        this.outpirctureurl = outpirctureurl == null ? null : outpirctureurl.trim();
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

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getDiscounttype() {
        return discounttype;
    }

    public void setDiscounttype(String discounttype) {
        this.discounttype = discounttype == null ? null : discounttype.trim();
    }

    public Integer getReserve1() {
        return reserve1;
    }

    public void setReserve1(Integer reserve1) {
        this.reserve1 = reserve1;
    }

    public String getReserve2() {
        return reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2 == null ? null : reserve2.trim();
    }

    public Integer getOther() {
        return other;
    }

    public void setOther(Integer other) {
        this.other = other;
    }

    public String getOther2() {
        return other2;
    }

    public void setOther2(String other2) {
        this.other2 = other2 == null ? null : other2.trim();
    }
}