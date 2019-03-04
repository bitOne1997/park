package com.park.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jpush.Jpush;
import com.park.model.AdminArgs;
import com.park.model.Constants;
import com.park.model.FeeCriterion;
import com.park.model.Outsideparkinfo;
import com.park.model.Park;
import com.park.model.PosChargeData;
import com.park.service.FeeCriterionService;
import com.park.service.FeeOperatorService;
import com.park.service.HttpUtil;
import com.park.service.JavaBeanXml;
import com.park.service.OutsideParkInfoService;
import com.park.service.ParkService;
import com.park.service.PosChargeDataService;
import com.park.service.YanchengDataService;


@Component
public class CalPosChargeTask {
	
	@Autowired
	PosChargeDataService chargeService;
	
	@Autowired
	ParkService parkService;
	
	@Autowired
	FeeCriterionService criterionService;
	@Autowired
	private OutsideParkInfoService outsideParkInfoService;
	@Autowired
	private FeeOperatorService feeOperatorService;
	@Autowired
	private JavaBeanXml javaBeanXml;
	@Autowired
	YanchengDataService yanchengDataService;
	
	@Value("#{prop.isNinebit}")
	private boolean isNinebit;
	
	@Scheduled(cron="0 0 18/1  * * ? ")
	public void cal(){	
		if (!isNinebit) {
			return;
		}
		List<PosChargeData> charges = chargeService.getUnCompleted();
		for(PosChargeData charge : charges){
			Date now = new Date();	
			Integer criterionId = parkService.getParkById(charge.getParkId()).getFeeCriterionId();
			FeeCriterion criterion = null;
			if(criterionId != null)
				criterion = criterionService.getById(criterionId);
			if(criterion == null)
				continue;
			int nightStartHour = Integer.parseInt(criterion.getNightstarttime().split(":")[0]);
			if(nightStartHour <= now.getHours()){
				now.setHours(nightStartHour);
				try {
//					chargeService.calExpense(charge, now, false);
//					charge.setChangeMoney(0);
//					charge.setChargeMoney(0);
//					charge.setPaidMoney(0);
//					charge.setGivenMoney(0);
//					charge.setIsOneTimeExpense(1);
//					charge.setEntranceDate(new SimpleDateFormat(Constants.DATEFORMAT).format(new Date()));
//					chargeService.insert(charge);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}	
	}
	@Scheduled(cron="0 30 0 * * ? ")
	public void dayInfo(){
		outsideParkInfoService.insertDayParkInfo();	
		if (AdminArgs.isGuest) {
			return;
		}
		Random rand = new Random();
		int a=rand.nextInt(25)+2;
		String url="http://park.hfcsbc.cn:8080/parkScreenPMS/ReceiveParkNum.action?parkId=3401040062&total=70&Surplus="+a;
		HttpUtil.get(url);
	}
	@Scheduled(cron="0 0/5 * * * ? ")
	public void parkUpdateFromXml() throws DocumentException{
		if (!isNinebit) {
			return;
		}
		javaBeanXml.updateParkFromXml();
	//	Jpush.SendPushToAllAudiences("heart!");
	}
	@Scheduled(cron="0 0 23 * * ? ")
	public void out(){
//		feeOperatorService.operatorsLogout();
//		List<PosChargeData> charges = chargeService.getUnCompleted();		
//		for(PosChargeData charge : charges){
//			Date now = new Date();
//			try {
//				chargeService.calExpense(charge, now, false);
//				charge.setChangeMoney(0);
//				charge.setChargeMoney(0);
//				charge.setPaidMoney(0);
//				charge.setGivenMoney(0);
//				charge.setEntranceDate(new SimpleDateFormat(Constants.DATEFORMAT).format(new Date()));
//				chargeService.insert(charge);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}
	
	@Scheduled(cron="*/8 * * * * ? ")
	public void updateYanchengData(){
	//	Park parkselect=parkService.getParkById(219);
//		String result=yanchengDataService.ParkLotFreeSpace("10028", " ", String.valueOf(parkselect.getPortCount()), String.valueOf(parkselect.getPortLeftCount()));
//		System.out.println(result);
	}
}
