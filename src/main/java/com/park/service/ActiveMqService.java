package com.park.service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.park.controller.BarrierChargeController;
import com.park.model.AdminArgs;
import com.park.model.PosChargeData;
import com.park.model.QueueDataCharge;

public class ActiveMqService {
	
	@Value("#{prop.ActiveMqUrl}")
	private String url;
	
	private static Log logger=LogFactory.getLog(ActiveMqService.class);
	public static void SendPosChargeData(String data){		
		Map<String, Object> args=new HashMap<>();
		args.put("data", data);
		logger.info("发送队列:"+data);
		HttpUtil.okHttpPost("http://parkserver.iotclouddashboard.com/parkServer/mq/poschargeData",args);
	}
	public static void SendWithQueueName(String data,String queue){
		Map<String, Object> args=new HashMap<>();
		args.put("data", data);
		logger.info("发送队列:"+data);
		args.put("queue", queue);
		HttpUtil.okHttpPost("http://parkserver.iotclouddashboard.com/parkServer/mq/queueData",args);
	}
	public static void SendTopicWithMac(PosChargeData data,String topic,String mac,int leftcount,int parkingType){
		QueueDataCharge queueDataCharge=new QueueDataCharge();
		queueDataCharge.setPlateNumber(data.getCardNumber());
	//	queueDataCharge.setChangeMoney(data.getChangeMoney());
		queueDataCharge.setChargeMoney(data.getChargeMoney());
		queueDataCharge.setDiscount(data.getDiscount());
		queueDataCharge.setParkingType(parkingType);
		queueDataCharge.setActionType(data.isEntrance()==true?"in":"out");
		
		if (data.getPayType()==10) {
			queueDataCharge.setActionType("notify");
			data.setPayType(0);
		}
	//	queueDataCharge.setDiscountType(data.getDiscountType());
	//	queueDataCharge.setEntrance(data.isEntrance());
		queueDataCharge.setEntranceDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data.getEntranceDate()));
		if (data.getExitDate()!=null) {
			queueDataCharge.setExitDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data.getExitDate()));
		}		
	//	queueDataCharge.setGivenMoney(data.getGivenMoney());
		queueDataCharge.setId(data.getId());
//		queueDataCharge.setIsOneTimeExpense(data.getIsOneTimeExpense());
		queueDataCharge.setIsLargeCar(data.getIsLargeCar()==true?1:0);
		queueDataCharge.setLeftCount(leftcount);
		queueDataCharge.setMac(mac);
		queueDataCharge.setOperatorId(data.getOperatorId());
		queueDataCharge.setOutUrl(data.getOutUrl());
	//	queueDataCharge.setOther(data.getOther());
	//	queueDataCharge.setOther2(data.getOther2());
	//	queueDataCharge.setPaidCompleted(data.isPaidCompleted());
		queueDataCharge.setPaidMoney(data.getPaidMoney());
		queueDataCharge.setParkDesc(data.getParkDesc());
		queueDataCharge.setParkId(data.getParkId());
		queueDataCharge.setPayType(data.getPayType());
	//	queueDataCharge.setPortNumber(data.getPortNumber());
		queueDataCharge.setPosId(data.getPosId());
		queueDataCharge.setRejectReason(data.getRejectReason());
//		queueDataCharge.setUnPaidMoney(data.getUnPaidMoney());
		queueDataCharge.setUrl(data.getUrl());
		
		Map<String, Object> args=new HashMap<>();
		args.put("data", JsonUtils.objectToJson(queueDataCharge));
		
		args.put("topic", topic);
		args.put("mac", mac);
	
		logger.info("发送topic:"+args);
		HttpUtil.okHttpPost("http://parkserver.iotclouddashboard.com/parkServer/mq/topicData",args);
	}
}
