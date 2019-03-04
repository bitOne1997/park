package com.park.model;

import com.alipay.api.AlipayClient;

public class AlipayChargeInfo {
	private PosChargeData posChargeData;
	private String serviceProvideId;
	private AlipayClient alipayClient;
	private boolean validate=true;
	
	
	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public PosChargeData getPosChargeData() {
		return posChargeData;
	}

	public void setPosChargeData(PosChargeData posChargeData) {
		this.posChargeData = posChargeData;
	}

	public String getServiceProvideId() {
		return serviceProvideId;
	}

	public void setServiceProvideId(String serviceProvideId) {
		this.serviceProvideId = serviceProvideId;
	}

	public AlipayClient getAlipayClient() {
		return alipayClient;
	}

	public void setAlipayClient(AlipayClient alipayClient) {
		this.alipayClient = alipayClient;
	}
}
