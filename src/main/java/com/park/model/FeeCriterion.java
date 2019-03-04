package com.park.model;

public class FeeCriterion {
    private Integer id;

    private String name;

    private Integer freemins;

    private Float step1price=(float) 1;;

    private Float step2price=(float) 2;

    private Float step3price=(float) 0;

    private Float step1pricelarge=(float) 2;

    private Float step2pricelarge=(float) 4;

    private Float step3pricelarge=(float) 0;

    private Integer step1capacity=60;

    private Integer step2capacity=480;

    private Integer timeoutpriceinterval=15;

    private Integer timeoutpriceinterval2=60;

    private Float maxexpense=(float) 9999;

    private String nightstarttime;

    private String nightendtime;

    private Integer isonetimeexpense=0;

    private Float onetimeexpense=(float)0;

    private String explaination;
   
	private Integer type=0;

    private String other;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getFreemins() {
        return freemins;
    }

    public void setFreemins(Integer freemins) {
        this.freemins = freemins;
    }

    public Float getStep1price() {
        return step1price;
    }

    public void setStep1price(Float step1price) {
        this.step1price = step1price;
    }

    public Float getStep2price() {
        return step2price;
    }

    public void setStep2price(Float step2price) {
        this.step2price = step2price;
    }

    public Float getStep3price() {
        return step3price;
    }

    public void setStep3price(Float step3price) {
        this.step3price = step3price;
    }

    public Float getStep1pricelarge() {
        return step1pricelarge;
    }

    public void setStep1pricelarge(Float step1pricelarge) {
        this.step1pricelarge = step1pricelarge;
    }

    public Float getStep2pricelarge() {
        return step2pricelarge;
    }

    public void setStep2pricelarge(Float step2pricelarge) {
        this.step2pricelarge = step2pricelarge;
    }

    public Float getStep3pricelarge() {
        return step3pricelarge;
    }

    public void setStep3pricelarge(Float step3pricelarge) {
        this.step3pricelarge = step3pricelarge;
    }

    public Integer getStep1capacity() {
        return step1capacity;
    }

    public void setStep1capacity(Integer step1capacity) {
        this.step1capacity = step1capacity;
    }

    public Integer getStep2capacity() {
        return step2capacity;
    }

    public void setStep2capacity(Integer step2capacity) {
        this.step2capacity = step2capacity;
    }

    public Integer getTimeoutpriceinterval() {
        return timeoutpriceinterval;
    }

    public void setTimeoutpriceinterval(Integer timeoutpriceinterval) {
        this.timeoutpriceinterval = timeoutpriceinterval;
    }

    public Integer getTimeoutpriceinterval2() {
        return timeoutpriceinterval2;
    }

    public void setTimeoutpriceinterval2(Integer timeoutpriceinterval2) {
        this.timeoutpriceinterval2 = timeoutpriceinterval2;
    }

    public Float getMaxexpense() {
        return maxexpense;
    }

    public void setMaxexpense(Float maxexpense) {
        this.maxexpense = maxexpense;
    }

    public String getNightstarttime() {
        return nightstarttime;
    }

    public void setNightstarttime(String nightstarttime) {
        this.nightstarttime = nightstarttime == null ? null : nightstarttime.trim();
    }

    public String getNightendtime() {
        return nightendtime;
    }

    public void setNightendtime(String nightendtime) {
        this.nightendtime = nightendtime == null ? null : nightendtime.trim();
    }

    public Integer getIsonetimeexpense() {
        return isonetimeexpense;
    }

    public void setIsonetimeexpense(Integer isonetimeexpense) {
        this.isonetimeexpense = isonetimeexpense;
    }

    public Float getOnetimeexpense() {
        return onetimeexpense;
    }

    public void setOnetimeexpense(Float onetimeexpense) {
        this.onetimeexpense = onetimeexpense;
    }

    public String getExplaination() {
        return explaination;
    }

    public void setExplaination(String explaination) {
        this.explaination = explaination == null ? null : explaination.trim();
    }
    
    
    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

}