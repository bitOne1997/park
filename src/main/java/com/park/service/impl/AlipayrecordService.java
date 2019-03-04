package com.park.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.park.controller.HongxingController;
import com.park.dao.AlipayrecordMapper;
import com.park.model.AlipayChargeInfo;
import com.park.model.Alipayrecord;
import com.park.model.Constants;
import com.park.model.Njcarfeerecord;
import com.park.model.PosChargeData;
import com.park.service.HongxingService;
import com.park.service.NjCarFeeRecordService;
import com.park.service.PosChargeDataService;
@Service
public class AlipayrecordService implements com.park.service.AlipayrecordService {

	@Autowired
	AlipayrecordMapper alipayrecordMapper;
	@Autowired
	NjCarFeeRecordService njCarFeeRecordService;
	@Autowired
	PosChargeDataService poschargedataService;
	@Autowired
	HongxingService hongxingService;
	
	private static Log logger = LogFactory.getLog(AlipayrecordService.class);
	String URL = "https://openapi.alipay.com/gateway.do";
	String APP_ID = Constants.alipayAppId;
	String FORMAT = "json";
	String CHARSET = "UTF-8";
	String SIGN_TYPE = "RSA";
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return alipayrecordMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Alipayrecord record) {
		// TODO Auto-generated method stub
		return alipayrecordMapper.insert(record);
	}

	@Override
	public int insertSelective(Alipayrecord record) {
		// TODO Auto-generated method stub
		return alipayrecordMapper.insertSelective(record);
	}

	@Override
	public Alipayrecord selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return alipayrecordMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Alipayrecord record) {
		// TODO Auto-generated method stub
		return alipayrecordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Alipayrecord record) {
		// TODO Auto-generated method stub
		return alipayrecordMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Alipayrecord> getByOutTradeNO(String outTradeNO) {
		// TODO Auto-generated method stub
		return alipayrecordMapper.getByOutTradeNO(outTradeNO);
	}

	@Override
	public List<Alipayrecord> getByPosChargeId(int poschargeId) {
		// TODO Auto-generated method stub
		return alipayrecordMapper.getByPosChargeId(poschargeId);
	}

	@Override
	public List<Alipayrecord> getByAliTradeNO(String aliTradeNo) {
		// TODO Auto-generated method stub
		return alipayrecordMapper.getByAliTradeNO(aliTradeNo);
	}

	@Override
	public AlipayChargeInfo getChargeDataByCarNumber(String carNumber) throws Exception {
		// TODO Auto-generated method stub
		
		AlipayClient alipayClient = new DefaultAlipayClient(URL, Constants.alipayAppId4, Constants.alipayPrivateKey4,
				FORMAT, CHARSET, Constants.alipayPublicKey4, SIGN_TYPE);
		AlipayClient alipayClient2 = new DefaultAlipayClient(URL, Constants.alipayAppId6, Constants.alipayPrivateKey6,
				FORMAT, CHARSET, Constants.alipayPublicKey6, SIGN_TYPE);
		List<PosChargeData> charges = poschargedataService.queryDebt(carNumber, new Date());
		AlipayChargeInfo alipayChargeInfo=new AlipayChargeInfo();
		alipayChargeInfo.setAlipayClient(alipayClient);
		alipayChargeInfo.setServiceProvideId("2088721707329444");
//		if (!charges.isEmpty()&&charges.get(0).getParkDesc()!=null&&charges.get(0).getParkDesc().contains("美凯龙")) {
//			charges.clear();
//		}
//		if (charges.isEmpty()) {//查找红星美凯龙的支付信息
//			alipayChargeInfo.setServiceProvideId("2088821579783141");
//			List<Njcarfeerecord> njcarfeerecords=njCarFeeRecordService.selectByCarNumber(carNumber);
//			if (njcarfeerecords.isEmpty()) {
//				alipayChargeInfo.setValidate(false);
//				return alipayChargeInfo;
//			}
//			Njcarfeerecord njcarfeerecord=njcarfeerecords.get(0);
//			String parkKey = "c1648ccf33314dc384155896cf4d00b9";
//			if (njcarfeerecord.getParkname().contains("家乐福")) {
//				parkKey = "ff8993a40b3a4249924f34044403b5bf";
//				alipayChargeInfo.setAlipayClient(alipayClient2);
//			}
//			Map<String, Object> data = null;
//			String orderCreate = "";
//			try {
//				data = hongxingService.getFeeByCarNumber(carNumber, parkKey);
//			} catch (Exception e) {
//				// TODO: handle exception
//				alipayChargeInfo.setValidate(false);
//				return alipayChargeInfo;
//			}
//			if (data == null) {
//				alipayChargeInfo.setValidate(false);
//				return alipayChargeInfo;
//			}
//			logger.info("红星费用:"+data.toString());
//			try {
//				String code = hongxingService.creatPayOrder((String) data.get("orderNo"), parkKey);
//				if (code == null) {
//					alipayChargeInfo.setValidate(false);
//					return alipayChargeInfo;
//				}
//				orderCreate = code;
//			} catch (Exception e) {
//				alipayChargeInfo.setValidate(false);
//				return alipayChargeInfo;
//			}
//			PosChargeData lastCharge = new PosChargeData();
//			lastCharge.setRejectReason(orderCreate);
//			lastCharge.setCardNumber(carNumber);
//			lastCharge.setParkId(3);
//			lastCharge.setParkDesc("美凯龙停车场");
//			lastCharge.setChargeMoney((double) data.get("totalAmount"));
//			if (njcarfeerecord.getParkname().contains("家乐福")) {
//				lastCharge.setParkId(217);
//				lastCharge.setParkDesc("家乐福停车场");
//				lastCharge.setChargeMoney((double) data.get("totalAmount"));
//			}
//			String enterTimeStr = ((String) data.get("enterTime")).replace("/", "-");
//			lastCharge.setEntranceDate(enterTimeStr);
//		//	lastCharge.setExitDate1(new Date());
//			lastCharge.setOperatorId((String) data.get("orderNo"));
//			poschargedataService.insert(lastCharge);
//		//	charges = poschargedataService.queryDebt(carNumber, new Date());
//		//	lastCharge = charges.get(0);
//			alipayChargeInfo.setPosChargeData(lastCharge);
//			
//			
//		}
//		else {
//			alipayChargeInfo.setPosChargeData(charges.get(0));
//		}
		if (!charges.isEmpty()) {
			alipayChargeInfo.setPosChargeData(charges.get(0));
		}
		else {
			alipayChargeInfo.setValidate(false);
		}
		return alipayChargeInfo;
	}

}
