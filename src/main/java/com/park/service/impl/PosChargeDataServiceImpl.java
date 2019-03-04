package com.park.service.impl;


import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.tools.config.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.dao.CarportStatusDetailDAO;
import com.park.dao.PosChargeDataDAO;
import com.park.dao.PosdataDAO;
import com.park.model.CarportStatusDetail;
import com.park.model.ChargedataParkWithTable;
import com.park.model.Constants;
import com.park.model.FeeCriterion;
import com.park.model.Feecriteriontopark;
import com.park.model.Monthuser;
import com.park.model.Outsideparkinfo;
import com.park.model.Park;
import com.park.model.Parkcarauthority;
import com.park.model.Parknoticeauthority;
import com.park.model.Parktoalipark;
import com.park.model.PosChargeData;
import com.park.model.Posdata;
import com.park.model.posdataReceive;
import com.park.service.ActiveMqService;
import com.park.service.AliParkFeeService;
import com.park.service.ChargeDataService;
import com.park.service.FeeCriterionService;
import com.park.service.FeecriterionToParkService;
import com.park.service.JedisClient;
import com.park.service.JsonUtils;
import com.park.service.MonthUserService;
import com.park.service.OutsideParkInfoService;
import com.park.service.ParkCarAuthorityService;
import com.park.service.ParkNoticeAuthorityService;
import com.park.service.ParkService;
import com.park.service.ParkToAliparkService;
import com.park.service.PosChargeDataService;
import com.park.service.PosdataService;

@Transactional
@Service
public class PosChargeDataServiceImpl implements PosChargeDataService {

	@Autowired
	MonthUserService monthUserService;
	@Autowired
	PosChargeDataDAO chargeDao;

	@Autowired
	ParkService parkService;

	@Autowired
	FormatTime formatTime;

	@Autowired
	private CarportStatusDetailDAO carportStatusDetailDAO;
	@Autowired
	ParkNoticeAuthorityService parkNoticeAuthorityService;
	
	@Autowired
	FeeCriterionService criterionService;

	@Autowired
	FeecriterionToParkService feecriterionToParkService;
	

	@Autowired
	private OutsideParkInfoService outsideParkInfoService;

	@Autowired
	ChargeDataService chargedataService;
	
	@Autowired
	AliParkFeeService aliparkFeeService;
	@Autowired
	ParkToAliparkService parkToAliparkService;
	
	@Resource(name="jedisClient")
	private JedisClient jedisClient;

	private static Log logger = LogFactory.getLog(PosChargeDataServiceImpl.class);

	@Override
	public PosChargeData getById(int id) {
		return chargeDao.getById(id);
	}

	@Override
	public List<PosChargeData> get() {
		return chargeDao.get();
	}

	@Override
	public List<PosChargeData> getUnCompleted() {
		return chargeDao.getUnCompleted();
	}

	@Override
	public List<PosChargeData> getPage(int low, int count) {
		return chargeDao.getPage(low, count);
	}

	@Override
	public int count() {
		return chargeDao.count();
	}

	@Override
	public int insert(PosChargeData item) {
		
		Parknoticeauthority parknoticeauthority=parkNoticeAuthorityService.getByParkId(item.getParkId()).get(0);
		if (parknoticeauthority!=null&&parknoticeauthority.getWeixin()==true) {
			Map<String, String> argstoali = new HashMap<>();
			argstoali.put("parkName", item.getParkDesc());
			argstoali.put("carNumber", item.getCardNumber());
			argstoali.put("enterTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			ActiveMqService.SendWithQueueName(JsonUtils.objectToJson(argstoali), "weixinEnterInfo");
		}
		int num=chargeDao.insert(item);
		//在插入之后 要不获取不到id
		if (ArrayUtils.contains(Constants.parkToQuene, item.getParkId())) {
			PosChargeData tmPosChargeData = item;
			tmPosChargeData.setExitDate1(tmPosChargeData.getEntranceDate());
			String str = JsonUtils.objectToJson(tmPosChargeData);
			item.setExitDate1(null);
			System.out.println("active插入前" + new Date().getTime());
			try {
				ActiveMqService.SendPosChargeData(str);
			} catch (Exception e) {
				// TODO: handle exception
			}
			System.out.println("active插入后" + new Date().getTime());
		}
		if (num==1&&false) {
			ChargedataParkWithTable chargedataParkWithTable=new ChargedataParkWithTable();
			chargedataParkWithTable.setTableName("chargeData_"+item.getParkId());
			chargedataParkWithTable.setParkdesc(item.getParkDesc());
			chargedataParkWithTable.setParkid(item.getParkId());
			chargedataParkWithTable.setCarnumber(item.getCardNumber());
			chargedataParkWithTable.setChangemoney((int) (item.getChangeMoney()*100));
			chargedataParkWithTable.setChargemoney((int) (item.getChargeMoney()*100));
			chargedataParkWithTable.setGivenmoney((int) (item.getGivenMoney()*100));
			chargedataParkWithTable.setInpictureurl(item.getUrl());
			chargedataParkWithTable.setOperatorid(item.getOperatorId());
			chargedataParkWithTable.setIslargecar(item.getIsLargeCar());
			chargedataParkWithTable.setIsonetimeexpense(item.getIsOneTimeExpense()>0?true:false);
			chargedataParkWithTable.setUnpaidmoney((int) (item.getUnPaidMoney()*100));
			chargedataParkWithTable.setPaidcompleted(item.isPaidCompleted());
			chargedataParkWithTable.setRejectreason(item.getRejectReason());
			chargedataParkWithTable.setDiscount((int) (item.getDiscount()*100));
			chargedataParkWithTable.setEntrancedate(item.getEntranceDate());
			chargedataParkWithTable.setDiscounttype(String.valueOf(item.getDiscountType()));
		//	chargedataService.insertTable(chargedataParkWithTable);
		}
		if (num==1) {
			Park park=parkService.getParkById(item.getParkId());
		//	park.setPortLeftCount();
			parkService.updateLeftPortCount(park.getId(),(park.getPortLeftCount()-1)>=0?(park.getPortLeftCount()-1):0);
		}
		return num;
	}

	@Override
	public int update(PosChargeData item) {
		return chargeDao.update(item);
	}

	@Override
	public List<PosChargeData> getCharges(String cardNumber) throws Exception {
		List<PosChargeData> charges = chargeDao.getDebt(cardNumber);

		for (PosChargeData tmpcharge : charges) {

			if (tmpcharge.getExitDate() == null) {
				this.calExpense(tmpcharge, new Date(), false);
		
			}
		}
		return charges;
	}

	@Override
	public List<PosChargeData> getDebt(String cardNumber) throws Exception {
		// System.out.println("获取poschargedata list前: "+new
		// Date().getTime()+"\n");
		List<PosChargeData> charges = chargeDao.getDebt(cardNumber);
		
		// System.out.println("获取poschargedata list后: "+new
		// Date().getTime()+"\n");
		List<PosChargeData> tmPosChargeDatas = new ArrayList<>();
		// System.out.println("poschargedata list录入支付宝前: "+new
		// Date().getTime()+"\n");
		
		for (PosChargeData charge : charges) {
			if (charge.getExitDate() == null) {
				// 信息录入支付宝
				List<Parktoalipark> parktoaliparks = parkToAliparkService.getByParkId(charge.getParkId());
				try {
					if (!parktoaliparks.isEmpty()) {
						Parktoalipark parktoalipark = parktoaliparks.get(0);
						Map<String, String> argstoali = new HashMap<>();
						argstoali.put("parking_id", parktoalipark.getAliparkingid());
						argstoali.put("car_number", cardNumber);
						argstoali.put("out_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
						ActiveMqService.SendWithQueueName(JsonUtils.objectToJson(argstoali), "aliExitInfo");
						// aliparkFeeService.parkingExitinfoSync(argstoali);
					}
					List<Parknoticeauthority> parkcarauthorities = parkNoticeAuthorityService.getByParkId(charge.getParkId());
					if (!parkcarauthorities.isEmpty() && (parkcarauthorities.get(0)).getAlipay() == true){
						Map<String, String> argstoali = new HashMap<>();
						argstoali.put("parking_id", "PI1501317472942184881");
						argstoali.put("car_number", cardNumber);
						argstoali.put("out_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
						ActiveMqService.SendWithQueueName(JsonUtils.objectToJson(argstoali), "aliExitInfo");
					}
					
				} catch (Exception e) {
					// TODO: handle exception
				}

				tmPosChargeDatas.add(charge);
			}
		}
		// System.out.println("poschargedata list录入支付宝后: "+new
		// Date().getTime()+"\n");
		// System.out.println("poschargedata 计算前: "+new Date().getTime()+"\n");
		for (PosChargeData tmpcharge : tmPosChargeDatas) {
			this.calExpense(tmpcharge, new Date(), false);
		}
		int tmpint = 0;
		for (PosChargeData tmpcharge : tmPosChargeDatas) {
			tmpint++;
			if (tmpint > 1) {
				tmpcharge.setChargeMoney(0);
				tmpcharge.setPaidCompleted(true);
				tmpcharge.setRejectReason("平台清场");
				update(tmpcharge);
			}
		}
		// System.out.println("poschargedata 计算后: "+new Date().getTime()+"\n");
		return tmPosChargeDatas;
	}

	@Override
	public List<PosChargeData> getLastRecord(String carNumber, int count) {
		return chargeDao.getLastRecord(carNumber, count);
	}

	@Override
	public PosChargeData pay(String cardNumber, double money) throws Exception {
		double theMoney = money;
		synchronized (this) {
			
		
		List<PosChargeData> charges = this.getCharges(cardNumber);
		// for (PosChargeData charge : charges) {
		// if (money >= charge.getUnPaidMoney()) {
		// money -= charge.getUnPaidMoney();
		// charge.setPaidCompleted(true);
		// this.update(charge);
		// }
		// }
		int count = charges.size();
		PosChargeData lastCharge = charges.get(0);
		money -= lastCharge.getUnPaidMoney();
		
		// Outsideparkinfo
		// outsideparkinfo=outsideParkInfoService.getByParkidAndDate(lastCharge.getParkId());
		if (money >= 0) {
			lastCharge.setGivenMoney(theMoney + lastCharge.getGivenMoney());
			lastCharge.setPaidCompleted(true);
			DecimalFormat df = new DecimalFormat("0.00");
			String data = df.format(lastCharge.getChangeMoney() + money);
			lastCharge.setChangeMoney(Double.parseDouble(data));
			// outsideparkinfo.setRealmoney((float)
			// (outsideparkinfo.getRealmoney()+lastCharge.getGivenMoney()-lastCharge.getChangeMoney()));
			// outsideparkinfo.setPossigndate(new Date());

		} else {
			lastCharge.setGivenMoney(theMoney + lastCharge.getGivenMoney());
			lastCharge.setUnPaidMoney(lastCharge.getUnPaidMoney() - theMoney);
			// outsideparkinfo.setRealmoney((float)
			// (outsideparkinfo.getRealmoney()+theMoney));
			// outsideparkinfo.setPossigndate(new Date());
		}
		// outsideParkInfoService.updateByPrimaryKeySelective(outsideparkinfo);
		this.update(lastCharge);
		return lastCharge;
		}
	}
	
	@Override
	public PosChargeData payWithOperatorId(String cardNumber, double money,String operatorId) throws Exception {
		double theMoney = money;
		List<PosChargeData> charges = this.getCharges(cardNumber);
		// for (PosChargeData charge : charges) {
		// if (money >= charge.getUnPaidMoney()) {
		// money -= charge.getUnPaidMoney();
		// charge.setPaidCompleted(true);
		// this.update(charge);
		// }
		// }
		int count = charges.size();
		PosChargeData lastCharge = charges.get(0);
		money -= lastCharge.getUnPaidMoney();

		// Outsideparkinfo
		// outsideparkinfo=outsideParkInfoService.getByParkidAndDate(lastCharge.getParkId());
		if (money >= 0) {
			lastCharge.setGivenMoney(theMoney + lastCharge.getGivenMoney());
			lastCharge.setPaidCompleted(true);
			DecimalFormat df = new DecimalFormat("0.00");
			String data = df.format(lastCharge.getChangeMoney() + money);
			lastCharge.setChangeMoney(Double.parseDouble(data));
			// outsideparkinfo.setRealmoney((float)
			// (outsideparkinfo.getRealmoney()+lastCharge.getGivenMoney()-lastCharge.getChangeMoney()));
			// outsideparkinfo.setPossigndate(new Date());

		} else {
			lastCharge.setGivenMoney(theMoney + lastCharge.getGivenMoney());
			lastCharge.setUnPaidMoney(lastCharge.getUnPaidMoney() - theMoney);
			// outsideparkinfo.setRealmoney((float)
			// (outsideparkinfo.getRealmoney()+theMoney));
			// outsideparkinfo.setPossigndate(new Date());
		}
		// outsideParkInfoService.updateByPrimaryKeySelective(outsideparkinfo);
		lastCharge.setOperatorId(operatorId);
		this.update(lastCharge);
		return lastCharge;
	}
	
	@Override
	public void calExpenseMulti(PosChargeData charge, Date exitDate, Boolean isQuery,Boolean isMultiFeeCtriterion,int carType) throws Exception {

		Boolean isMonthUser = false;
		Boolean isRealMonthUser = false;
		// Boolean isMonthUserMoreCars=false;
		List<Monthuser> monthusers = monthUserService.getByCarnumberAndPark(charge.getCardNumber(), charge.getParkId());
		Monthuser monthuserUse = new Monthuser();
		for (Monthuser monthuser : monthusers) {
			if (monthuser.getType() == 0) {
				Long diff = (monthuser.getEndtime().getTime() - (new Date()).getTime());
				if (diff > 0) {
					isMonthUser = true;
					monthuserUse = monthuser;
					isRealMonthUser = true;
					break;
				}
			}
		}
		Park park = parkService.getParkById(charge.getParkId());
		Boolean isMultiCarsOneCarport = false;
		if (park.getDescription() != null && park.getDescription().contains("一位多车")) {
			isMultiCarsOneCarport = true;
		}

		if (isMultiCarsOneCarport && isRealMonthUser && monthuserUse.getPlatecolor() != null
				&& monthuserUse.getPlatecolor().equals("包月转为临停")) {
			isRealMonthUser = false;
			monthuserUse.setPlatecolor("出场完结");
			monthUserService.updateByPrimaryKeySelective(monthuserUse);
		}
		if (isMultiCarsOneCarport && isRealMonthUser && monthuserUse.getPlatecolor() != null
				&& monthuserUse.getPlatecolor().equals("临停恢复为包月")) {
			isRealMonthUser = false;
			List<Monthuser> monthuserss = monthUserService.getByParkAndPort(monthuserUse.getParkid(),
					monthuserUse.getCardnumber());

			for (Monthuser monthuser : monthuserss) {
				if (isMultiCarsOneCarport && isRealMonthUser && monthuserUse.getPlatecolor() != null
						&& monthuser.getPlatecolor().contains("包月出场")) {
					String[] datas = monthuser.getPlatecolor().split("#");
					try {
						Date dateout = new SimpleDateFormat(Constants.DATEFORMAT).parse(datas[1]);
						charge.setExitDate1(dateout);// 更改出场时间为原先包月车的出场时间
						exitDate = dateout;
						charge.setRejectReason("临停转包月");
					} catch (Exception e) {
						// TODO: handle exception
					}
					monthuser.setPlatecolor("出场完结");
					monthUserService.updateByPrimaryKeySelective(monthuser);
					monthuserUse.setPlatecolor("出场完结");
					for (Monthuser strMonthUser : monthuserss) {
						if (strMonthUser.getPlatecolor().contains("包月转为临停")) {
							monthuserUse.setPlatecolor(
									"包月出场#" + new SimpleDateFormat(Constants.DATEFORMAT).format(new Date()));
							strMonthUser.setPlatecolor("临停恢复为包月");
							monthUserService.updateByPrimaryKeySelective(strMonthUser);
							break;
						}
					}
					monthUserService.updateByPrimaryKeySelective(monthuserUse);
					break;
				}
			}

		}
		if (isRealMonthUser) {
			charge.setChargeMoney(0);
			charge.setUnPaidMoney(0);
			charge.setExitDate1(exitDate);
			charge.setPaidCompleted(true);
			if (!isQuery) {
				if (isMultiCarsOneCarport && monthuserUse.getPlatecolor().equals("多车包月入场")) {
					monthuserUse.setPlatecolor("出场完结");
					List<Monthuser> monthuserss = monthUserService.getByUsernameAndPark(monthuserUse.getOwner(),
							monthuserUse.getParkid());
					for (Monthuser monthuser : monthuserss) {
						if (monthuser.getPlatecolor().equals("包月转为临停")) {
							monthuser.setPlatecolor("临停恢复为包月");
							monthuserUse.setPlatecolor(
									"包月出场#" + new SimpleDateFormat(Constants.DATEFORMAT).format(new Date()));
							monthUserService.updateByPrimaryKeySelective(monthuser);
							break;
						}
					}
					monthUserService.updateByPrimaryKeySelective(monthuserUse);
				}
				this.update(charge);
			}
			return;
		}

		Integer criterionId = park.getFeeCriterionId();
		FeeCriterion criterion = criterionService.getById(criterionId);

		if (criterion.getType() == 2) {
			newFeeCalcExpense2(charge, criterion, exitDate, isQuery);
			if (isRealMonthUser) {
				charge.setChargeMoney(0);
				charge.setUnPaidMoney(0);
			}
			if (!isQuery) {
				this.update(charge);
			}
			return;
		}
		if (criterion.getType() == 1) {
			newFeeCalcExpense1(charge, criterion, exitDate, isQuery);
			if (isRealMonthUser) {
				charge.setChargeMoney(0);
				charge.setUnPaidMoney(0);
			}
			if (!isQuery) {
				this.update(charge);
			}
			return;
		}

		String startTime = new SimpleDateFormat(Constants.DATEFORMAT).format(charge.getEntranceDate());
		String endTime = new SimpleDateFormat(Constants.DATEFORMAT).format(exitDate);
		String nightStartHour = "20";
		String nightEndHour = "07";
		if (criterion.getNightstarttime() != null && criterion.getNightendtime() != null) {
			nightStartHour = criterion.getNightstarttime().split(":")[0];
			nightEndHour = criterion.getNightendtime().split(":")[0];
		}
		int isOneTimeExpense = charge.getIsOneTimeExpense();
		if (charge.getIsLargeCar() == false) {
			Map<String, String> dates = formatTime.format(startTime, endTime, nightStartHour, nightEndHour);
			for (String name : dates.keySet()) {
				charge.setEntranceDate(name);
				int houra = Integer.parseInt(name.substring(11, 13));
				int hourNight = Integer.parseInt(nightStartHour);
				int hourDay = Integer.parseInt(nightEndHour);
				boolean isNight = false;
				if (houra > 12) {
					if (houra >= hourNight) {
						isNight = true;
					}
				} else {
					if (houra < hourDay) {
						isNight = true;
					}
				}
				if (isNight) {
					charge.setIsOneTimeExpense(1);
					this.calExpenseSmallCar(charge, new SimpleDateFormat(Constants.DATEFORMAT).parse(dates.get(name)),
							isQuery);
				} else if (isOneTimeExpense == 0) {
					charge.setIsOneTimeExpense(0);
					this.calExpenseSmallCar(charge, new SimpleDateFormat(Constants.DATEFORMAT).parse(dates.get(name)),
							isQuery);
				} else {
					charge.setIsOneTimeExpense(1);
					this.calExpenseSmallCar(charge, new SimpleDateFormat(Constants.DATEFORMAT).parse(dates.get(name)),
							isQuery);
				}
			}
			// System.out.println("计费计算后: "+new Date().getTime()+"\n");
			// System.out.println("计费更新前: "+new Date().getTime()+"\n");
			charge.setEntranceDate(startTime);
			charge.setExitDate(endTime);
			if (charge.getPaidMoney() >= charge.getChargeMoney() && !isQuery) {
				charge.setUnPaidMoney(0);
				charge.setPaidCompleted(true);
				charge.setChangeMoney(charge.getPaidMoney() - charge.getChargeMoney());
			} else {
				charge.setUnPaidMoney(charge.getChargeMoney() - charge.getPaidMoney());
			}
			if (isRealMonthUser) {
				charge.setChargeMoney(0);
				charge.setUnPaidMoney(0);
			}
			if (!isQuery) {
				this.update(charge);
			}
			// System.out.println("计费更新后: "+new Date().getTime()+"\n");

		} else {
			Map<String, String> dates = formatTime.format(startTime, endTime, nightStartHour, nightEndHour);
			for (String name : dates.keySet()) {
				charge.setEntranceDate(name);
				int houra = Integer.parseInt(name.substring(11, 13));
				int hourNight = Integer.parseInt(nightStartHour);
				int hourDay = Integer.parseInt(nightEndHour);
				boolean isNight = false;
				if (houra > 12) {
					if (houra >= hourNight) {
						isNight = true;
					}
				} else {
					if (houra < hourDay) {
						isNight = true;
					}
				}
				if (isNight) {
					charge.setIsOneTimeExpense(1);
					this.calExpenseLargeCar(charge, new SimpleDateFormat(Constants.DATEFORMAT).parse(dates.get(name)),
							isQuery);
				} else if (isOneTimeExpense == 0) {
					charge.setIsOneTimeExpense(0);
					this.calExpenseLargeCar(charge, new SimpleDateFormat(Constants.DATEFORMAT).parse(dates.get(name)),
							isQuery);
				} else {
					charge.setIsOneTimeExpense(1);
					this.calExpenseLargeCar(charge, new SimpleDateFormat(Constants.DATEFORMAT).parse(dates.get(name)),
							isQuery);
				}
			}
			charge.setEntranceDate(startTime);
			charge.setExitDate(endTime);
			if (charge.getPaidMoney() >= charge.getChargeMoney() && !isQuery) {
				charge.setUnPaidMoney(0);
				charge.setPaidCompleted(true);
				charge.setChangeMoney(charge.getPaidMoney() - charge.getChargeMoney());
			} else {
				charge.setUnPaidMoney(charge.getChargeMoney() - charge.getPaidMoney());
			}
			if (isRealMonthUser) {
				// charge.setPaidMoney(0);
				charge.setChargeMoney(0);
				charge.setUnPaidMoney(0);
			}
			if (!isQuery) {
				this.update(charge);
			}

		}

	}

	@Override
	public List<PosChargeData> getDebt(String cardNumber, Date exitDate) throws Exception {
		// TODO Auto-generated method stub
		List<PosChargeData> charges = chargeDao.getDebt(cardNumber);
		List<PosChargeData> tmPosChargeDatas = new ArrayList<>();
		for (PosChargeData charge : charges) {
			// 信息录入支付宝
			if (charge.getExitDate() == null) {
				List<Parktoalipark> parktoaliparks = parkToAliparkService.getByParkId(charge.getParkId());
				try {
					if (!parktoaliparks.isEmpty()) {
						Parktoalipark parktoalipark = parktoaliparks.get(0);
						Map<String, String> argstoali = new HashMap<>();
						argstoali.put("parking_id", parktoalipark.getAliparkingid());
						argstoali.put("car_number", cardNumber);
						argstoali.put("out_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(exitDate));
						ActiveMqService.SendWithQueueName(JsonUtils.objectToJson(argstoali), "aliExitInfo");
						// aliparkFeeService.parkingExitinfoSync(argstoali);
					}
					List<Parknoticeauthority> parkcarauthorities = parkNoticeAuthorityService.getByParkId(charge.getParkId());
					if (!parkcarauthorities.isEmpty() && (parkcarauthorities.get(0)).getAlipay() == true){
						Map<String, String> argstoali = new HashMap<>();
						argstoali.put("parking_id", "PI1501317472942184881");
						argstoali.put("car_number", cardNumber);
						argstoali.put("out_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
						ActiveMqService.SendWithQueueName(JsonUtils.objectToJson(argstoali), "aliExitInfo");
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (charge.getChargeMoney()==0) {
					charge.setPaidCompleted(true);
					update(charge);
				}
				tmPosChargeDatas.add(charge);
			}
		}
		for (PosChargeData tmpcharge : tmPosChargeDatas) {
			Park park = parkService.getParkById(tmpcharge.getParkId());
			if (park.getType() == 1) {
				this.calExpenseType1(tmpcharge, exitDate, false);
			} else {
				this.calExpense(tmpcharge, exitDate, false);
			}

		}
		int tmpint = 0;
//		for (PosChargeData tmpcharge : tmPosChargeDatas) {
//			tmpint++;
//			if (tmpint > 1) {
//				tmpcharge.setChargeMoney(0);
//				tmpcharge.setRejectReason("平台清场");
//				update(tmpcharge);
//			}
//		}
		return tmPosChargeDatas;
	}

	@Override
	public void calExpenseLargeCar(PosChargeData charge, Date exitDate, Boolean isQuery) throws Exception {
		Park park = parkService.getParkById(charge.getParkId());

		Integer criterionId = park.getFeeCriterionId();

		if (criterionId == null)
			throw new Exception("no fee criterion");
		FeeCriterion criterion = criterionService.getById(criterionId);
		if (criterion.getIsonetimeexpense() != null && criterion.getIsonetimeexpense().intValue() == 1) {
			charge.setIsOneTimeExpense(1);
		}
		charge.setExitDate1(exitDate);
		double expense = 0;
		float diffMin = (charge.getExitDate().getTime() - charge.getEntranceDate().getTime()) / (1000 * 60f);
		if (charge.getIsOneTimeExpense() == 1 && diffMin >= criterion.getFreemins()) {
			expense = criterion.getOnetimeexpense();
		} else {
			float firstHour = criterion.getStep1capacity();
			if (diffMin > criterion.getFreemins()) {
				if (diffMin <= firstHour) {
					double intervals = Math
							.ceil((diffMin - criterion.getFreemins()) / criterion.getTimeoutpriceinterval());
					expense = intervals * criterion.getStep1pricelarge();
				} else {
					double intervals1 = Math
							.ceil((firstHour - criterion.getFreemins()) / criterion.getTimeoutpriceinterval());
					expense = intervals1 * criterion.getStep1pricelarge();
					double intervals2 = Math.ceil((diffMin - firstHour) / (criterion.getTimeoutpriceinterval()));
					// double intervals2 = Math.ceil((diffMin - firstHour) /
					// (criterion.getTimeoutpriceinterval()/2));

					expense += intervals2 * criterion.getStep2pricelarge();
				}
			}
		}
		charge.setChargeMoney(expense + charge.getChargeMoney());
		if (charge.getChargeMoney() > (criterion.getMaxexpense() * 2)) {
			charge.setChargeMoney(criterion.getMaxexpense() * 2);
		}
	}

	@Override
	public void calExpenseSmallCar(PosChargeData charge, Date exitDate, Boolean isQuery) throws Exception {

		Park park = parkService.getParkById(charge.getParkId());

		Integer criterionId = park.getFeeCriterionId();

		if (criterionId == null)
			throw new Exception("no fee criterion");

		FeeCriterion criterion = criterionService.getById(criterionId);

		charge.setExitDate1(exitDate);
		double expense = 0;
		if (criterion.getIsonetimeexpense() != null && criterion.getIsonetimeexpense().intValue() == 1) {
			charge.setIsOneTimeExpense(1);
		}
		float diffMin = (float) Math
				.ceil((charge.getExitDate().getTime() - charge.getEntranceDate().getTime()) / (1000 * 60f));
		if (charge.getIsOneTimeExpense() == 1 && diffMin >= criterion.getFreemins()) {
			expense = criterion.getOnetimeexpense();
		} else {

			float firstHour = criterion.getStep1capacity();

			if (diffMin > criterion.getFreemins()) {
				if (diffMin <= firstHour) {
					double intervals = Math
							.ceil((diffMin - criterion.getFreemins()) / criterion.getTimeoutpriceinterval());
					expense = intervals * criterion.getStep1price();
				} else {
					double intervals1 = Math
							.ceil((firstHour - criterion.getFreemins()) / criterion.getTimeoutpriceinterval());
					expense = intervals1 * criterion.getStep1price();
					double intervals2 = Math.ceil((diffMin - firstHour) / (criterion.getTimeoutpriceinterval()));
					// double intervals2 = Math.ceil((diffMin - firstHour) /
					// (criterion.getTimeoutpriceinterval()/2));
					expense += intervals2 * criterion.getStep2price();
				}
			}
		}
		if (expense > criterion.getMaxexpense()) {
			//charge.setChargeMoney(criterion.getMaxexpense());
			expense=criterion.getMaxexpense();
		}
		charge.setChargeMoney(expense + charge.getChargeMoney());
		

	}

	@Override
	public List<PosChargeData> queryDebt(String cardNumber, Date exitDate) throws Exception {
		// TODO Auto-generated method stub
		List<PosChargeData> charges = chargeDao.getDebt(cardNumber);
		for (PosChargeData charge : charges) {
			if (charge.getExitDate() == null) {
				Park park = parkService.getParkById(charge.getParkId());
				if (park.getType() == 1) {
					this.calExpenseType1(charge, exitDate, true);
				} else {
					this.calExpense(charge, exitDate, true);
				}
			}
		}
		return charges;
	}
	@Override
	public List<PosChargeData> queryDebtWithParkId(String cardNumber, Date exitDate,Integer parkId) throws Exception {
		// TODO Auto-generated method stub
		List<PosChargeData> charges = chargeDao.getDebtWithParkId(cardNumber, parkId);
		for (PosChargeData charge : charges) {
			if (charge.getExitDate() == null) {
				Park park = parkService.getParkById(charge.getParkId());
				if (park.getType() == 1) {
					this.calExpenseType1(charge, exitDate, true);
				} else {
					this.calExpense(charge, exitDate, true);
				}
			}
		}
		return charges;
	}

	@Override
	public List<PosChargeData> selectPosdataByParkAndRange(Date startDay, Date endDay, int parkId) {
		// TODO Auto-generated method stub
		return chargeDao.selectPosdataByParkAndRange(startDay, endDay, parkId);
	}

	@Override
	public Map<String, Object> getParkChargeByDay(int parkId, String day) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date parsedStartDay = null;
		try {
			parsedStartDay = sdf.parse(day + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date parsedEndDay = null;
		try {
			parsedEndDay = sdf.parse(day + " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<PosChargeData> posChargeDatas = selectPosdataByParkAndRange(parsedStartDay, parsedEndDay, parkId);
		Map<String, Object> retmap = new HashMap<>();
		float chargeTotal = 0;
		float realReceiveMoney = 0;
		for (PosChargeData posData : posChargeDatas) {
			chargeTotal += posData.getChargeMoney();
			realReceiveMoney += posData.getGivenMoney() + posData.getPaidMoney() - posData.getChangeMoney();
		}
		retmap.put("totalMoney", chargeTotal);
		retmap.put("realMoney", realReceiveMoney);
		return retmap;
	}

	@Override
	public List<PosChargeData> queryCurrentDebt(String cardNumber, Date exitDate) throws Exception {
		List<PosChargeData> charges = chargeDao.getDebt(cardNumber);
		List<PosChargeData> tmPosChargeDatas = new ArrayList<>();
		for (PosChargeData charge : charges) {
			if (charge.getExitDate() == null) {
				tmPosChargeDatas.add(charge);
			}
		}
		for (PosChargeData tmpcharge : tmPosChargeDatas) {
			this.calExpense(tmpcharge, exitDate, true);
		}
		return tmPosChargeDatas;
	}

	@Override
	public List<PosChargeData> getByCardNumber(String cardNumber) {
		// TODO Auto-generated method stub
		return chargeDao.getByCardNumber(cardNumber);
	}

	@Override
	public List<PosChargeData> getByParkName(String parkName) {
		// TODO Auto-generated method stub
		return chargeDao.getByParkName(parkName);
	}

	@Override
	public List<PosChargeData> repay(String cardNumber, double money) throws Exception {
		double theMoney = money;
		List<PosChargeData> charges = this.getCharges(cardNumber);
		for (PosChargeData charge : charges) {
			if (money >= charge.getUnPaidMoney()) {
				money -= charge.getUnPaidMoney();
				charge.setPaidCompleted(true);
				this.update(charge);
			}
		}

		if (money >= 0) {
			int count = charges.size();
			PosChargeData lastCharge = charges.get(0);
			lastCharge.setChangeMoney(lastCharge.getChangeMoney() + money);
			lastCharge.setGivenMoney(theMoney + lastCharge.getGivenMoney());
			// Outsideparkinfo
			// outsideparkinfo=outsideParkInfoService.getByParkidAndDate(lastCharge.getParkId());
			// outsideparkinfo.setRealmoney((float)
			// (outsideparkinfo.getRealmoney()+lastCharge.getPaidMoney()+lastCharge.getGivenMoney()-lastCharge.getChangeMoney()));
			// outsideparkinfo.setPossigndate(new Date());
			// outsideParkInfoService.updateByPrimaryKeySelective(outsideparkinfo);
			this.update(lastCharge);
		}
		return charges;
	}

	@Override
	public List<Map<String, Object>> getFeeOperatorChargeData(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return chargeDao.getFeeOperatorChargeData(startDate, endDate);
	}

	@Override
	public List<PosChargeData> getByRange(int parkId, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return chargeDao.getByRange(parkId, startDate, endDate);
	}

	@Override
	public List<PosChargeData> getByParkAndDay(int parkId, String date) throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat sFormat = new SimpleDateFormat(Constants.DATEFORMAT);
		Date startDate = sFormat.parse(date + " 00:00:00");
		Date endDate = sFormat.parse(date + " 23:59:59");
		return chargeDao.getByRange(parkId, startDate, endDate);
	}

	@Override
	public List<PosChargeData> getAllByDay(String date) throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat sFormat = new SimpleDateFormat(Constants.DATEFORMAT);
		Date startDate = sFormat.parse(date + " 00:00:00");
		Date endDate = sFormat.parse(date + " 23:59:59");
		return chargeDao.getAllByDay(startDate, endDate);
	}

	@Override
	public List<PosChargeData> getByParkAndDayRange(int parkId, String startDate, String endDate)
			throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat sFormat = new SimpleDateFormat(Constants.DATEFORMAT);
		Date dstartDate = sFormat.parse(startDate + " 00:00:00");
		Date dendDate = sFormat.parse(endDate + " 23:59:59");
		return chargeDao.getByRange(parkId, dstartDate, dendDate);
	}

	@Override
	public List<PosChargeData> getAllByDayRange(String startDate, String endDate) throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat sFormat = new SimpleDateFormat(Constants.DATEFORMAT);
		Date dstartDate = sFormat.parse(startDate + " 00:00:00");
		Date dendDate = sFormat.parse(endDate + " 23:59:59");
		return chargeDao.getAllByDay(dstartDate, dendDate);
	}

	@Override
	public List<PosChargeData> getPageArrearage(int low, int count) {
		// TODO Auto-generated method stub
		return chargeDao.getPageArrearage(low, count);
	}

	@Override
	public List<PosChargeData> getPageArrearageByParkId(int parkId, int start, int count) {
		// TODO Auto-generated method stub
		return chargeDao.getPageArrearageByParkId(parkId, start, count);
	}

	@Override
	public List<PosChargeData> getPageByParkId(int parkId, int start, int count) {
		// TODO Auto-generated method stub
		return chargeDao.getPageByParkId(parkId, start, count);
	}

	@Override
	public List<PosChargeData> getByParkIdAndCardNumber(Integer parkId, String cardNumber) {
		// TODO Auto-generated method stub
		return chargeDao.getByParkIdAndCardNumber(parkId, cardNumber);
	}

	@Override
	public List<PosChargeData> getParkCarportStatusToday(int parkId, Date tmpdate) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String day = sdf.format(tmpdate) + " 00:00:00";
		return chargeDao.getParkCarportStatusToday(parkId, day);
	}

	@Override
	public Outsideparkinfo getOutsideparkinfoByOrigin(int parkId, String day) {
		// TODO Auto-generated method stub
		Park park = parkService.getParkById(parkId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date parsedStartDay = null;
		try {
			parsedStartDay = sdf.parse(day + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date parsedEndDay = null;
		try {
			parsedEndDay = sdf.parse(day + " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Outsideparkinfo outsideparkinfo = new Outsideparkinfo();
//		Outsideparkinfo outsideparkinfo2 = new Outsideparkinfo();
//		outsideparkinfo2 = outsideParkInfoService.getByParkidAndDate(parkId, new Date());
//
//		if (outsideparkinfo2 != null && outsideparkinfo2.getPossigndate() != null) {
//			outsideparkinfo.setPossigndate(outsideparkinfo2.getPossigndate());
//		}
	//	List<PosChargeData> posChargeDatas = selectPosdataByParkAndRange(parsedStartDay, parsedEndDay, parkId);
		Map<String, Object> parkSumData = selectPosdataSumByParkAndRange(parsedStartDay, parsedEndDay, parkId);
	//	System.out.println(parkSumData.toString());
		Map<String, Object> parkInCount = calInByParkAndRange(parkId, parsedStartDay, parsedEndDay);
	//	System.out.println(parkInCount.toString());
		Map<String, Object> parkOutCount = calOutByParkAndRange(parkId, parsedStartDay, parsedEndDay);
	//	System.out.println(parkOutCount.toString());
		outsideparkinfo.setParkid(parkId);
		outsideparkinfo.setCarportcount(park.getPortCount());
		outsideparkinfo.setUnusedcarportcount(park.getPortLeftCount());

		outsideparkinfo.setAmountmoney( parkSumData==null?0:(float)(double) parkSumData.get("chargeMoney"));
		outsideparkinfo.setRealmoney((parkSumData==null?0:(float)(double) parkSumData.get("givenMoney")) + (parkSumData==null?0:(float)(double) parkSumData.get("paidMoney"))
				- (parkSumData==null?0:(float)(double) parkSumData.get("changeMoney")));
		outsideparkinfo.setEntrancecount(parkInCount==null?0:(int)(long) parkInCount.get("count"));
		outsideparkinfo.setOutcount(parkOutCount==null?0:(int)(long) parkOutCount.get("count"));
		outsideparkinfo.setArrearage(outsideparkinfo.getAmountmoney() - outsideparkinfo.getRealmoney());
		// if (!posChargeDatas.isEmpty()) {
		// if (outsideparkinfo2!=null&&outsideparkinfo2.getPossigndate()==null)
		// {
		// outsideparkinfo.setPossigndate(posChargeDatas.get(0).getEntranceDate());
		// }
		// else
		// if(outsideparkinfo2!=null&&posChargeDatas.get(0).getEntranceDate().getTime()>outsideparkinfo2.getPossigndate().getTime()){
		// outsideparkinfo.setPossigndate(posChargeDatas.get(0).getEntranceDate());
		// }
		// //
		// outsideparkinfo.setAmountmoney((float)parkSumData.get("chargeMoney"));
		// //
		// outsideparkinfo.setRealmoney((float)parkSumData.get("givenMoney")+(float)parkSumData.get("paidMoney")-(float)parkSumData.get("changeMoney"));
		// outsideparkinfo.setEntrancecount((int)parkInCount.get("count"));
		// outsideparkinfo.setOutcount((int)parkOutCount.get("count"));
		// outsideparkinfo.setArrearage(outsideparkinfo.getAmountmoney()-outsideparkinfo.getRealmoney());
		// for(PosChargeData posChargeData : posChargeDatas){
		// outsideparkinfo.setEntrancecount(outsideparkinfo.getEntrancecount()+1);
		// outsideparkinfo.setAmountmoney((float)
		// (outsideparkinfo.getAmountmoney()+posChargeData.getChargeMoney()));
		// if (posChargeData.getExitDate()==null) {
		// outsideparkinfo.setUnusedcarportcount(outsideparkinfo.getUnusedcarportcount()-1);
		// }
		// else {
		// outsideparkinfo.setOutcount(outsideparkinfo.getOutcount()+1);
		// }
		// outsideparkinfo.setRealmoney((float)
		// (outsideparkinfo.getRealmoney()+posChargeData.getGivenMoney()+posChargeData.getPaidMoney()-posChargeData.getChangeMoney()));
		// if(!posChargeData.isPaidCompleted())
		// {
		// if
		// (posChargeData.getChargeMoney()>(posChargeData.getPaidMoney()+posChargeData.getGivenMoney()))
		// {
		// outsideparkinfo.setArrearage((float)
		// (outsideparkinfo.getArrearage()+posChargeData.getChargeMoney()-posChargeData.getPaidMoney()-posChargeData.getGivenMoney()));
		// }
		// // outsideparkinfo.setRealmoney((float)
		// (outsideparkinfo.getRealmoney()+posChargeData.getPaidMoney()));
		// }
		// }
		// }
		// else {
		// List<Posdata>
		// posdatas=posdataService.selectPosdataByParkAndRange(park.getName(),
		// parsedStartDay, parsedEndDay);
		// //outsideparkinfo=outsideParkInfoService.getByParkidAndDate(parkId);
		// //从数据表中获取数据
		// if (posdatas.isEmpty()) {
		// return outsideparkinfo;
		// }
		// outsideparkinfo.setPossigndate(posdatas.get(0).getStarttime());
		// for(Posdata posdata:posdatas){
		// outsideparkinfo.setAmountmoney(outsideparkinfo.getAmountmoney()+posdata.getMoney().floatValue());
		//
		// if (posdata.getIsarrearage()==false) {
		// if (posdata.getMode()==0) {
		// outsideparkinfo.setEntrancecount(outsideparkinfo.getEntrancecount()+1);
		// outsideparkinfo.setUnusedcarportcount(outsideparkinfo.getUnusedcarportcount()-1);
		// }
		// else {
		// outsideparkinfo.setOutcount(outsideparkinfo.getOutcount()+1);
		// outsideparkinfo.setUnusedcarportcount(outsideparkinfo.getUnusedcarportcount()+1);
		// outsideparkinfo.setRealmoney(outsideparkinfo.getRealmoney()+posdata.getGiving().floatValue()+posdata.getRealmoney().floatValue()-posdata.getReturnmoney().floatValue());
		// }
		// }
		//
		// else {
		// outsideparkinfo.setRealmoney(outsideparkinfo.getRealmoney()+posdata.getGiving().floatValue()+posdata.getRealmoney().floatValue()-posdata.getReturnmoney().floatValue());
		// }
		//
		// }
		/*
		 * List<Posdata>
		 * posdatas=posdataService.selectPosdataByParkAndRange(park.getName(),
		 * parsedStartDay, parsedEndDay);
		 * outsideparkinfo.setPossigndate(posdatas.get(0).getStarttime());
		 * for(Posdata posdata :posdatas){
		 * outsideparkinfo.setEntrancecount(outsideparkinfo.getEntrancecount()+1
		 * ); outsideparkinfo.setAmountmoney(outsideparkinfo.getAmountmoney()+
		 * posdata.getMoney().floatValue()); if (posdata.getEndtime()==null) {
		 * outsideparkinfo.setUnusedcarportcount(outsideparkinfo.
		 * getUnusedcarportcount()-1); } else{
		 * outsideparkinfo.setOutcount(outsideparkinfo.getOutcount()+1); } if
		 * (posdata.get) {
		 * 
		 * } }
		 */
		// }
	//	System.out.println("返回结果:"+outsideparkinfo);
		return outsideparkinfo;
	}
	
	@Override
	public Outsideparkinfo getOutsideparkinfoByOriginMoney(int id,String day) {
		// TODO Auto-generated method stub
		/*Park park = parkService.getParkById(parkId);*/
		/*Park park =new Park();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date parsedStartDay = null;
		try {
			parsedStartDay = sdf.parse(day + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date parsedEndDay = null;
		try {
			parsedEndDay = sdf.parse(day + " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Outsideparkinfo outsideparkinfo = new Outsideparkinfo();
		Map<String, Object> parkSumData = selectPosdataSumByParkAndRangeMoney(id,parsedStartDay, parsedEndDay);
		Map<String, Object> parkInCount = calInByParkAndRangeMoney(parsedStartDay, parsedEndDay);
		Map<String, Object> parkOutCount = calOutByParkAndRangeMoney(parsedStartDay, parsedEndDay);
		outsideparkinfo.setParkid(parkId);
		outsideparkinfo.setCarportcount(park.getPortCount());
		outsideparkinfo.setUnusedcarportcount(park.getPortLeftCount());

		outsideparkinfo.setAmountmoney( parkSumData==null?0:(float)(double) parkSumData.get("chargeMoney"));
		outsideparkinfo.setRealmoney((parkSumData==null?0:(float)(double) parkSumData.get("givenMoney")) + (parkSumData==null?0:(float)(double) parkSumData.get("paidMoney"))
				- (parkSumData==null?0:(float)(double) parkSumData.get("changeMoney")));
		outsideparkinfo.setEntrancecount(parkInCount==null?0:(int)(long) parkInCount.get("count"));
		outsideparkinfo.setOutcount(parkOutCount==null?0:(int)(long) parkOutCount.get("count"));
		outsideparkinfo.setArrearage(outsideparkinfo.getAmountmoney() - outsideparkinfo.getRealmoney());*/
		return null;
	}

	@Override
	public List<PosChargeData> getArrearageByCardNumber(String cardNumber) {
		// TODO Auto-generated method stub
		return chargeDao.getArrearageByCardNumber(cardNumber);
	}

	@Override
	public List<PosChargeData> getByParkAuthority(String userName) {
		// TODO Auto-generated method stub
		List<Park> parkList = parkService.getParks();
		if (userName != null)
			parkList = parkService.filterPark(parkList, userName);
		int num = 120 / parkList.size();
		if (num < 1) {
			num = 2;
		}
		List<PosChargeData> posChargeDatas = new ArrayList<PosChargeData>();
		for (Park park : parkList) {
			List<PosChargeData> tmPosChargeDatas = getPageByParkId(park.getId(), 0, num);
			posChargeDatas.addAll(tmPosChargeDatas);
		}
		return posChargeDatas;
	}

	@Override
	public Integer deleteByParkIdAndDate(int parkId, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return chargeDao.deleteByParkIdAndDate(startDate, endDate, parkId);
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return chargeDao.deleteById(id);
	}

	@Override
	public List<PosChargeData> selectPosdataByParkAndRangeAndCarportNumber(Date startDay, Date endDay, int parkId,
			int carportNumber) {
		// TODO Auto-generated method stub
		return chargeDao.selectPosdataByParkAndRangeAndCarportNumber(startDay, endDay, parkId, carportNumber);
	}

	@Override
	public List<PosChargeData> hardwareRecord(Integer parkId, String startDate, String endDate) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, Object>> carportStatusDetails = carportStatusDetailDAO.getDetailByParkIdAndDateRange(parkId,
				startDate, endDate);
		List<PosChargeData> posChargeDatas = new ArrayList<>();
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Map<String, Object> c : carportStatusDetails) {
			if (c.get("endTime") != null) {
				PosChargeData tmPosChargeData = new PosChargeData();
				// tmPosChargeData.setCardNumber(Integer.toString(c.getCarportId()));
				tmPosChargeData.setEntranceDate(sFormat.format(c.get("startTime")));
				tmPosChargeData.setExitDate(sFormat.format(c.get("endTime")));
				tmPosChargeData.setParkId(parkId);
				tmPosChargeData.setPortNumber(String.valueOf(c.get("carportNumber")));
				tmPosChargeData.setIsLargeCar(false);
				tmPosChargeData.setIsOneTimeExpense(0);
				posChargeDatas.add(tmPosChargeData);
			}
		}
		for (PosChargeData p : posChargeDatas) {
			this.calExpense(p, p.getExitDate(), true);
		}
		return posChargeDatas;
	}

	@Override
	public PosChargeData updateRejectReason(String cardNumber, String rejectReason) throws Exception {
		// TODO Auto-generated method stub
		List<PosChargeData> charges = this.getCharges(cardNumber);
		if (charges.isEmpty()) {
			return null;
		}
		PosChargeData lastCharge = charges.get(0);
		lastCharge.setRejectReason(rejectReason);
		this.update(lastCharge);
		return lastCharge;
	}

	@Override
	public List<PosChargeData> getByCardNumberAndPort(String cardNumber, Integer portNumber) {
		// TODO Auto-generated method stub
		return chargeDao.getByCardNumberAndPort(cardNumber, portNumber);
	}
	
	@Override
	public void calExpenseType1(PosChargeData charge, Date exitDate, Boolean isQuery) throws ParseException {
		// TODO Auto-generated method stub
		String startTime = new SimpleDateFormat(Constants.DATEFORMAT).format(charge.getEntranceDate());
		String endTime = new SimpleDateFormat(Constants.DATEFORMAT).format(exitDate);
		Park park = parkService.getParkById(charge.getParkId());
		Integer criterionId = park.getFeeCriterionId();
		FeeCriterion criterion = criterionService.getById(criterionId);
		String nightStartHour = "24";
		String nightEndHour = "0";
		Map<String, String> dates = formatTime.format(startTime, endTime, nightStartHour, nightEndHour);
		double money = 0;
		for (String name : dates.keySet()) {
			Date startDate = new SimpleDateFormat(Constants.DATEFORMAT).parse(name);
			Date endDate = new SimpleDateFormat(Constants.DATEFORMAT).parse(dates.get(name));
			long diffs = endDate.getTime() - startDate.getTime() - criterion.getFreemins() * 60 * 1000;
			if (diffs > 1000 * 60 * 60 * 12) {
				money += 10;
			}
			if (diffs <= 1000 * 60 * 60 * 12 && diffs > 0) {
				money += 10;
			}
		}
		charge.setExitDate(endTime);
		if (!isQuery) {
			this.update(charge);
		}
	}

	@Override
	public PosChargeData newFeeCalcExpense(PosChargeData charge, Date exitDate, Boolean isQuery) throws Exception {
		// TODO Auto-generated method stub
		if (exitDate == null) {
			exitDate = new Date();
		}
		charge.setExitDate1(exitDate);
		Park park = parkService.getParkById(charge.getParkId());
		Integer criterionId = park.getFeeCriterionId();
		FeeCriterion criterion = criterionService.getById(criterionId);
		// 入场免费时间处理
		int freeMinutes = criterion.getFreemins();

		Date entranceDate = new Date(charge.getEntranceDate().getTime() + freeMinutes * 60 * 1000);

		String startTime = new SimpleDateFormat(Constants.DATEFORMAT).format(entranceDate);
		String endTime = new SimpleDateFormat(Constants.DATEFORMAT).format(exitDate);
		String nightStartHour = "20";
		String nightEndHour = "8";
		if (criterion.getNightstarttime() != null && criterion.getNightendtime() != null) {
			nightStartHour = criterion.getNightstarttime().split(":")[0];
			nightEndHour = criterion.getNightendtime().split(":")[0];
		}
		if (entranceDate.getTime() > exitDate.getTime()) {
			charge.setChargeMoney(0);
			return charge;
		}
		Map<String, String> dates = formatTime.format(startTime, endTime, nightStartHour, nightEndHour);
		double money = 0;
		// for(String name:dates.keySet()){
		// Date startDate=new
		// SimpleDateFormat(Constants.DATEFORMAT).parse(name);
		// Date endDate=new
		// SimpleDateFormat(Constants.DATEFORMAT).parse(dates.get(name));
		// String nightStartHour2 = "20";
		// String nightEndHour2 = "8";
		// Map<String,String> dates2=formatTime.format(new
		// SimpleDateFormat(Constants.DATEFORMAT).format(startDate), new
		// SimpleDateFormat(Constants.DATEFORMAT).format(endDate),
		// nightStartHour2,nightEndHour2);
		// for(String name2:dates2.keySet()){
		// Date startDate2=new
		// SimpleDateFormat(Constants.DATEFORMAT).parse(name2);
		// Date endDate2=new
		// SimpleDateFormat(Constants.DATEFORMAT).parse(dates2.get(name2));
		//
		// }
		// long
		// diffs=endDate.getTime()-startDate.getTime()-criterion.getFreemins()*60*1000;
		// if (diffs>1000*60*60*12) {
		// money+=10;
		// }
		// if (diffs<=1000*60*60*12&&diffs>0) {
		// money+=10;
		// }
		// }

		double step1Price = criterion.getStep1price();
		double step2Price = criterion.getStep2price();
		int step1Minutes = 15;
		int step2minutes = 60;
		if (charge.getIsLargeCar()) {
			step1Price = criterion.getStep1pricelarge();
			step2Price = criterion.getStep2pricelarge();
		}
		for (String name : dates.keySet()) {
			logger.info("计费**车牌号:" + charge.getCardNumber() + "时间:" + name + "-" + dates.get(name));
			Date startDate = new SimpleDateFormat(Constants.DATEFORMAT).parse(name);
			Date endDate = new SimpleDateFormat(Constants.DATEFORMAT).parse(dates.get(name));
			String startd = name.substring(11, 13);
			if (Integer.parseInt(startd) >= Integer.parseInt(nightEndHour)
					&& Integer.parseInt(startd) < Integer.parseInt(nightStartHour)) {
				long diffs = endDate.getTime() - startDate.getTime();
				double hours = Math.ceil((double) diffs / (1000 * 60 * step1Minutes));
				money += hours * step1Price;
			} else {
				long diffs = endDate.getTime() - startDate.getTime();
				double hours = Math.ceil((double) diffs / (1000 * 60 * step2minutes));
				money += hours * step2Price;
			}
			logger.info("计费**车牌号:" + charge.getCardNumber() + "费用:" + money);
		}
		charge.setChargeMoney(money);
		return charge;
	}
	//24小时
	@Override
	public PosChargeData newFeeCalcExpense4(PosChargeData charge, FeeCriterion criterion, Date exitDate,
			Boolean isQuery,Park park) throws Exception {
		Date enterDate=charge.getEntranceDate();
		Date enterDateNew=new Date(enterDate.getTime()+criterion.getFreemins()*1000*60);
		long diff=(long) Math.ceil((exitDate.getTime()-enterDateNew.getTime())/(1000*60));
		
		int rate=1;
		if (charge.getIsLargeCar()) {
			if (StringUtils.isNotBlank(criterion.getOther())) {
				String ctrOther=criterion.getOther();
				if (ctrOther.contains("MaxRateToSmallCar")) {
					rate=2;
				}
			}
		}
		
		if (diff<=0) {
			charge.setChargeMoney(0);
			charge.setEntranceDate1(enterDate);
			charge.setExitDate1(new Date());
			return charge;
		}
		int leftMinuts=(int) Math.ceil((diff%(60*24))) ;
		int days=(int) (diff/(60*24));
		double money=days*criterion.getMaxexpense()*rate;
		Date newEnterTime=new Date(exitDate.getTime()-leftMinuts*1000*60-criterion.getFreemins()*1000*60);
		charge.setEntranceDate1(newEnterTime);
		if (charge.getIsLargeCar()) {
			calExpenseLargeCarWithData(charge, exitDate, isQuery, park, criterion);
		}
		else {
			calExpenseSmallCarWithData(charge, exitDate, isQuery,park, criterion);
		}
		charge.setEntranceDate1(enterDate);
		charge.setChargeMoney(charge.getChargeMoney()+money);		
		return charge;
	}
	// 按次
	@Override
	public PosChargeData newFeeCalcExpense1(PosChargeData charge, FeeCriterion criterion, Date exitDate,
			Boolean isQuery) throws Exception {
		if (exitDate == null) {
			exitDate = new Date();
		}
		charge.setExitDate1(exitDate);
		Date entranceDate = new Date(charge.getEntranceDate().getTime() + criterion.getFreemins() * 60 * 1000);
		if (entranceDate.getTime() >= exitDate.getTime()) {
			charge.setChargeMoney(0);
			return charge;
		}
		entranceDate = new Date(charge.getEntranceDate().getTime());
		float price = criterion.getStep1price();
		if (charge.getIsLargeCar()) {
			price = criterion.getStep1pricelarge();
		}
		if (criterion.getIsonetimeexpense() == 0) {
			double diff = exitDate.getTime() - entranceDate.getTime();
			double size = Math.ceil((diff / (1000 * 60)) / criterion.getTimeoutpriceinterval());
			charge.setChargeMoney(price * size);
		} else {
			String nightStartHour = "24";
			String nightEndHour = "0";
			String startTime = new SimpleDateFormat(Constants.DATEFORMAT).format(entranceDate);
			String endTime = new SimpleDateFormat(Constants.DATEFORMAT).format(exitDate);
			Map<String, String> dates = formatTime.format(startTime, endTime, nightStartHour, nightEndHour);
			charge.setChargeMoney(price * dates.size());
		}
		return charge;
	}

	@Override
	public PosChargeData newFeeCalcExpense2(PosChargeData charge, FeeCriterion criterion, Date exitDate,
			Boolean isQuery) throws Exception {
		if (exitDate == null) {
			exitDate = new Date();
		}
		charge.setExitDate1(exitDate);

		// 入场免费时间处理
		int freeMinutes = criterion.getFreemins();

		Date entranceDate = new Date(charge.getEntranceDate().getTime() + freeMinutes * 60 * 1000);

		String startTime = new SimpleDateFormat(Constants.DATEFORMAT).format(entranceDate);
		String endTime = new SimpleDateFormat(Constants.DATEFORMAT).format(exitDate);
		String nightStartHour = "20";
		String nightEndHour = "8";
		if (criterion.getNightstarttime() != null && criterion.getNightendtime() != null) {
			nightStartHour = criterion.getNightstarttime().split(":")[0];
			nightEndHour = criterion.getNightendtime().split(":")[0];
		}
		if (entranceDate.getTime() > exitDate.getTime()) {
			charge.setChargeMoney(0);
			return charge;
		}
		Map<String, String> dates = formatTime.format(startTime, endTime, nightStartHour, nightEndHour);
		double money = 0;

		double step1Price = criterion.getStep1price();
		double step2Price = criterion.getStep3price();
		int step1Minutes = 15;
		int step2minutes = 60;
		step1Minutes=criterion.getTimeoutpriceinterval();
		step2minutes=criterion.getTimeoutpriceinterval2();
		if (charge.getIsLargeCar()) {
			step1Price = criterion.getStep1pricelarge();
			step2Price = criterion.getStep3pricelarge();
		}
		for (String name : dates.keySet()) {
			logger.info("计费**车牌号:" + charge.getCardNumber() + "时间:" + name + "-" + dates.get(name));
			Date startDate = new SimpleDateFormat(Constants.DATEFORMAT).parse(name);
			Date endDate = new SimpleDateFormat(Constants.DATEFORMAT).parse(dates.get(name));
			String startd = name.substring(11, 13);
			if (Integer.parseInt(startd) >= Integer.parseInt(nightEndHour)
					&& Integer.parseInt(startd) < Integer.parseInt(nightStartHour)) {
				long diffs = endDate.getTime() - startDate.getTime();
				double hours = Math.ceil((double) diffs / (1000 * 60 * step1Minutes));
				if (hours * step1Price>criterion.getMaxexpense()) {
					money+=criterion.getMaxexpense();
				}
				else {
					money += hours * step1Price;
				}
				
				logger.info("计费**车牌号:" + charge.getCardNumber() + "费用:" + hours * step1Price);
			} else {
				long diffs = endDate.getTime() - startDate.getTime();
				double hours = Math.ceil((double) diffs / (1000 * 60 * step2minutes));
				if (hours * step2Price>criterion.getMaxexpense()) {
					money+=criterion.getMaxexpense();
				}
				else {
					money += hours * step2Price;
				}
				logger.info("计费**车牌号:" + charge.getCardNumber() + "费用:" + hours * step2Price);
			}
			logger.info("计费**车牌号:" + charge.getCardNumber() + "累积费用:" + money);
		}
		charge.setChargeMoney(money);
		return charge;
	}

	@Override
	public List<PosChargeData> getByCardNumberAndPark(String cardNumber, Integer parkId) {
		// TODO Auto-generated method stub
		return chargeDao.getByCardNumberAndPark(cardNumber, parkId);
	}

	@Override
	public Map<String, Object> getParkChargeCountByDay(int parkId, String day) {
		// TODO Auto-generated method stub

		Park park = parkService.getParkById(parkId);
		String parkName = park.getName();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date parsedStartDay = null;
		try {
			parsedStartDay = sdf.parse(day + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date parsedEndDay = null;
		try {
			parsedEndDay = sdf.parse(day + " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<PosChargeData> posChargeDatas = selectPosdataByParkAndRange(parsedStartDay, parsedEndDay, parkId);
		Map<String, Object> retmap = new HashMap<>();
		int outcount = 0;
		if (posChargeDatas.isEmpty()) {
			retmap.put("in", 0);
			retmap.put("out", 0);
		} else {
			for (PosChargeData posChargeData : posChargeDatas) {
				if (posChargeData.getExitDate() != null) {
					outcount += 1;
				}
			}
			retmap.put("in", posChargeDatas.size());
			retmap.put("out", outcount);
		}

		return retmap;
	}

	@Override
	public List<PosChargeData> getDebtWithData(String cardNumber, List<Parktoalipark> parktoaliparks,
			List<Monthuser> monthusers, Park park,Boolean isMultiFeeCtriterion,int carType) throws Exception {
		List<PosChargeData> charges = new ArrayList<>();
		try {
			String redisId=jedisClient.get("P-"+park.getId()+"-"+cardNumber);
			if (redisId!=null) {
				logger.info("redis获取poschargedataId:"+redisId);
				charges.add(chargeDao.getById(Integer.parseInt(redisId)));
				if (charges.get(0).isPaidCompleted()) {
					charges.clear();
					return charges;
				}
			}
			else {
				charges = chargeDao.getDebtWithParkId(cardNumber, park.getId());
			}
		} catch (Exception e) {
			charges = chargeDao.getDebtWithParkId(cardNumber, park.getId());
		}
	
		logger.info(""+cardNumber+"取得未付款记录"+ charges.size()+"条");
		List<PosChargeData> tmPosChargeDatas = new ArrayList<>();
		if (charges.isEmpty()) {
			return charges;
		}
		for (PosChargeData charge : charges) {
			logger.info(charge.getCardNumber()+"入场"+charge.getEntranceDate()+" "+charge.getParkDesc());
			if (charge.getExitDate() == null) {
				tmPosChargeDatas.add(charge);
			}

		}

		if (!parktoaliparks.isEmpty() && !tmPosChargeDatas.isEmpty()&&tmPosChargeDatas.get(0).getExitDate() == null) {
			// 信息录入支付宝
			try {
				Parktoalipark parktoalipark = parktoaliparks.get(0);
				Map<String, String> argstoali = new HashMap<>();
				argstoali.put("parking_id", parktoalipark.getAliparkingid());
				argstoali.put("car_number", cardNumber);
				argstoali.put("out_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				ActiveMqService.SendWithQueueName(JsonUtils.objectToJson(argstoali), "aliExitInfo");
				
				
				List<Parknoticeauthority> parkcarauthorities = parkNoticeAuthorityService.getByParkId(tmPosChargeDatas.get(0).getParkId());
				if (!parkcarauthorities.isEmpty() && (parkcarauthorities.get(0)).getAlipay() == true){
					Map<String, String> argstoali2 = new HashMap<>();
					argstoali2.put("parking_id", "PI1501317472942184881");
					argstoali2.put("car_number", cardNumber);
					argstoali2.put("out_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					ActiveMqService.SendWithQueueName(JsonUtils.objectToJson(argstoali2), "aliExitInfo");
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	//	this.calExpenseMulti(tmPosChargeDatas.get(0), new Date(), false,isMultiFeeCtriterion,carType);
		FeeCriterion criterion=null;
		if (isMultiFeeCtriterion) {
			List<Feecriteriontopark> feecriteriontoparks=feecriterionToParkService.getByParkAndType(park.getId(), carType);
			if (!feecriteriontoparks.isEmpty()) {
				criterion=criterionService.getById(feecriteriontoparks.get(0).getCriterionid());
			}
		}
		else {
			criterion=criterionService.getById(park.getFeeCriterionId());
		}
		if (criterion==null) {
			criterion=criterionService.getById(park.getFeeCriterionId());
		}
		if (!tmPosChargeDatas.isEmpty()) {
			this.calExpensewithData(tmPosChargeDatas.get(0), new Date(), false, monthusers, park, criterion);
			try {
				jedisClient.del("P-"+park.getId()+"-"+cardNumber);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		int tmpint = 0;
		for (PosChargeData tmpcharge : tmPosChargeDatas) {
			tmpint++;
			if (tmpint > 1) {
				tmpcharge.setChargeMoney(0);
				tmpcharge.setExitDate1(new Date());
				tmpcharge.setPaidCompleted(true);
				tmpcharge.setRejectReason("平台清场");
				update(tmpcharge);
			}
		}
		return tmPosChargeDatas;
	}

	@Override
	public void calExpenseSmallCarWithData(PosChargeData charge, Date exitDate, Boolean isQuery, Park park,
			FeeCriterion criterion) {
		
		charge.setExitDate1(exitDate);
		double expense = 0;
		if (criterion.getIsonetimeexpense() != null && criterion.getIsonetimeexpense().intValue() == 1) {
			charge.setIsOneTimeExpense(1);
		}
		float diffMin = (float) Math
				.ceil((charge.getExitDate().getTime() - charge.getEntranceDate().getTime()) / (1000 * 60f));
		if (charge.getIsOneTimeExpense() == 1 && diffMin >= criterion.getFreemins()) {
			expense = criterion.getOnetimeexpense();
		} else {

			float firstHour = criterion.getStep1capacity();

			if (diffMin > criterion.getFreemins()) {
				if (diffMin <= firstHour) {
					double intervals = Math
							.ceil((diffMin - criterion.getFreemins()) / criterion.getTimeoutpriceinterval());
					expense = intervals * criterion.getStep1price();
				} else {
					double intervals1 = Math
							.ceil((firstHour - criterion.getFreemins()) / criterion.getTimeoutpriceinterval());
					expense = intervals1 * criterion.getStep1price();
					double intervals2 = Math.ceil((diffMin - firstHour) / (criterion.getTimeoutpriceinterval()));
					// double intervals2 = Math.ceil((diffMin - firstHour) /
					// (criterion.getTimeoutpriceinterval()/2));
					expense += intervals2 * criterion.getStep2price();
				}
			}
		}
		if (expense > criterion.getMaxexpense()) {
			expense=criterion.getMaxexpense();
		}
		charge.setChargeMoney(expense + charge.getChargeMoney());
		
		logger.info(charge.getCardNumber()+" "+charge.getEntranceDate()+" "+exitDate+"calExpenseSmallCarWithData费用"+charge.getChargeMoney());
	}

	@Override
	public void calExpenseLargeCarWithData(PosChargeData charge, Date exitDate, Boolean isQuery, Park park,
			FeeCriterion criterion) {
		int rate=1;
		if (StringUtils.isNotBlank(criterion.getOther())) {
			String ctrOther=criterion.getOther();
			if (ctrOther.contains("MaxRateToSmallCar")) {
				rate=2;
			}
		}
		
		if (criterion.getIsonetimeexpense() != null && criterion.getIsonetimeexpense().intValue() == 1) {
			charge.setIsOneTimeExpense(1);
		}
		charge.setExitDate1(exitDate);
		double expense = 0;
		float diffMin = (charge.getExitDate().getTime() - charge.getEntranceDate().getTime()) / (1000 * 60f);
		if (charge.getIsOneTimeExpense() == 1 && diffMin >= criterion.getFreemins()) {
			expense = criterion.getOnetimeexpense();
		} else {
			float firstHour = criterion.getStep1capacity();
			if (diffMin > criterion.getFreemins()) {
				if (diffMin <= firstHour) {
					double intervals = Math
							.ceil((diffMin - criterion.getFreemins()) / criterion.getTimeoutpriceinterval());
					expense = intervals * criterion.getStep1pricelarge();
				} else {
					double intervals1 = Math
							.ceil((firstHour - criterion.getFreemins()) / criterion.getTimeoutpriceinterval());
					expense = intervals1 * criterion.getStep1pricelarge();
					double intervals2 = Math.ceil((diffMin - firstHour) / (criterion.getTimeoutpriceinterval()));
					// double intervals2 = Math.ceil((diffMin - firstHour) /
					// (criterion.getTimeoutpriceinterval()/2));

					expense += intervals2 * criterion.getStep2pricelarge();
				}
			}
		}
		if (expense > criterion.getMaxexpense()*rate) {
			expense=criterion.getMaxexpense()*rate;
		}
		charge.setChargeMoney(expense + charge.getChargeMoney());
//		if (charge.getChargeMoney() > (criterion.getMaxexpense() * 2)) {
//			charge.setChargeMoney(criterion.getMaxexpense() * 2);
//		}
		logger.info(charge.getCardNumber()+" "+charge.getEntranceDate()+" "+exitDate+"calExpenseLargeCarWithData费用"+charge.getChargeMoney());
	}

	@Override
	public List<PosChargeData> getLastRecordWithPark(String carNumber, int count, int parkId) {
		// TODO Auto-generated method stub
		return chargeDao.getLastRecordWithPark(carNumber, count, parkId);
	}

	@Override
	public void calExpensewithData(PosChargeData charge, Date exitDate, Boolean isQuery, List<Monthuser> monthusers,
			Park park, FeeCriterion criterion) throws Exception {
		Boolean isMonthUser = false;
		Boolean isRealMonthUser = false;
		Monthuser monthuserUse = new Monthuser();
		
		for (Monthuser monthuser : monthusers) {
			if (monthuser.getType() == 0) {
				Long diff = (monthuser.getEndtime().getTime() - (new Date()).getTime());
				if (diff > 0&&monthuser.getPlatenumber().equals(charge.getCardNumber())) {
					isMonthUser = true;
					monthuserUse = monthuser;
					isRealMonthUser = true;
					break;
				}
			}
		}
		Boolean isMultiCarsOneCarport = false;
		if (park.getDescription() != null && park.getDescription().contains("一位多车")) {
			isMultiCarsOneCarport = true;
		}
		logger.info(charge.getCardNumber()+" 是否月卡:"+isRealMonthUser+" 计费标准:"+criterion.getName());
		if (isMultiCarsOneCarport && isRealMonthUser && monthuserUse.getPlatecolor() != null
				&& monthuserUse.getPlatecolor().equals("包月转临停")) {
			isRealMonthUser = false;
			monthuserUse.setPlatecolor("出场完结");
			monthUserService.updateByPrimaryKeySelective(monthuserUse);
		}
		//包月转临停
		if (charge.getParkDesc().contains("包月转临停")) {
			isRealMonthUser = false;
		}

		if (isRealMonthUser) {
			logger.info(charge.getCardNumber()+"月卡结算完毕!");
			charge.setChargeMoney(0);
			charge.setUnPaidMoney(0);
			charge.setExitDate1(exitDate);
			charge.setPaidCompleted(true);
			if (!isQuery) {
				if (isMultiCarsOneCarport && monthuserUse.getPlatecolor().equals("多车包月入场")) {
					monthuserUse.setPlatecolor("出场完结");
					List<Monthuser> monthuserss = monthUserService.getByUsernameAndPark(monthuserUse.getOwner(),
							monthuserUse.getParkid());

					monthUserService.updateByPrimaryKeySelective(monthuserUse);
				}
				this.update(charge);
			}
			return;
		}

		if (criterion.getType() == 2) {
			newFeeCalcExpense2(charge, criterion, exitDate, isQuery);
			if (isRealMonthUser) {
				charge.setChargeMoney(0);
				charge.setUnPaidMoney(0);
			}
			if (!isQuery) {
				this.update(charge);
			}
			return;
		}
		if (criterion.getType() == 1) {//按次
			newFeeCalcExpense1(charge, criterion, exitDate, isQuery);
			if (isRealMonthUser) {
				charge.setChargeMoney(0);
				charge.setUnPaidMoney(0);
			}
			if (!isQuery) {
				this.update(charge);
			}
			return;
		}
		
		if (criterion.getType() == 4) {//24小时
			newFeeCalcExpense4(charge, criterion, exitDate, isQuery,park);
			if (isRealMonthUser) {
				charge.setChargeMoney(0);
				charge.setUnPaidMoney(0);
			}
			if (!isQuery) {
				this.update(charge);
			}
			return;
		}
		//三个计费阶段
		if (criterion.getType()==6) {
			newFeeCalcExpense6(charge, criterion, exitDate, isQuery,park);
			if (isRealMonthUser) {
				charge.setChargeMoney(0);
				charge.setUnPaidMoney(0);
			}
			if (!isQuery) {
				this.update(charge);
			}
			return;
		}
		
		String startTime = new SimpleDateFormat(Constants.DATEFORMAT).format(charge.getEntranceDate());
		String endTime = new SimpleDateFormat(Constants.DATEFORMAT).format(exitDate);
		String nightStartHour = "20";
		String nightEndHour = "07";
		if (criterion.getNightstarttime() != null && criterion.getNightendtime() != null) {
			nightStartHour = criterion.getNightstarttime().split(":")[0];
			nightEndHour = criterion.getNightendtime().split(":")[0];
		}
		int isOneTimeExpense = charge.getIsOneTimeExpense();
		if (charge.getIsLargeCar() == false) {
			Map<String, String> dates = formatTime.format(startTime, endTime, nightStartHour, nightEndHour);
			for (String name : dates.keySet()) {
				charge.setEntranceDate(name);
				int houra = Integer.parseInt(name.substring(11, 13));
				int hourNight = Integer.parseInt(nightStartHour);
				int hourDay = Integer.parseInt(nightEndHour);
				boolean isNight = false;
				if (houra > 12) {
					if (houra >= hourNight) {
						isNight = true;
					}
				} else {
					if (houra < hourDay) {
						isNight = true;
					}
				}
				if (isNight) {
					charge.setIsOneTimeExpense(1);
					this.calExpenseSmallCarWithData(charge,
							new SimpleDateFormat(Constants.DATEFORMAT).parse(dates.get(name)), isQuery, park,
							criterion);
				} else if (isOneTimeExpense == 0) {
					charge.setIsOneTimeExpense(0);
					this.calExpenseSmallCarWithData(charge,
							new SimpleDateFormat(Constants.DATEFORMAT).parse(dates.get(name)), isQuery, park,
							criterion);
				} else {
					charge.setIsOneTimeExpense(1);
					this.calExpenseSmallCarWithData(charge,
							new SimpleDateFormat(Constants.DATEFORMAT).parse(dates.get(name)), isQuery, park,
							criterion);
				}
			}
			charge.setEntranceDate(startTime);
			charge.setExitDate(endTime);
			if (charge.getPaidMoney() >= charge.getChargeMoney() && !isQuery) {
				charge.setUnPaidMoney(0);
				charge.setPaidCompleted(true);
				charge.setChangeMoney(charge.getPaidMoney() - charge.getChargeMoney());
			} else {
				charge.setUnPaidMoney(charge.getChargeMoney() - charge.getPaidMoney());
			}
			if (isRealMonthUser) {
				charge.setChargeMoney(0);
				charge.setUnPaidMoney(0);
			}
			if (!isQuery) {
				this.update(charge);
			}
		} else {
			Map<String, String> dates = formatTime.format(startTime, endTime, nightStartHour, nightEndHour);
			for (String name : dates.keySet()) {
				charge.setEntranceDate(name);
				int houra = Integer.parseInt(name.substring(11, 13));
				int hourNight = Integer.parseInt(nightStartHour);
				int hourDay = Integer.parseInt(nightEndHour);
				boolean isNight = false;
				if (houra > 12) {
					if (houra >= hourNight) {
						isNight = true;
					}
				} else {
					if (houra < hourDay) {
						isNight = true;
					}
				}
				if (isNight) {
					charge.setIsOneTimeExpense(1);
					this.calExpenseLargeCarWithData(charge,
							new SimpleDateFormat(Constants.DATEFORMAT).parse(dates.get(name)), isQuery, park,
							criterion);
				} else if (isOneTimeExpense == 0) {
					charge.setIsOneTimeExpense(0);
					this.calExpenseLargeCarWithData(charge,
							new SimpleDateFormat(Constants.DATEFORMAT).parse(dates.get(name)), isQuery, park,
							criterion);
				} else {
					charge.setIsOneTimeExpense(1);
					this.calExpenseLargeCarWithData(charge,
							new SimpleDateFormat(Constants.DATEFORMAT).parse(dates.get(name)), isQuery, park,
							criterion);
				}
			}
			charge.setEntranceDate(startTime);
			charge.setExitDate(endTime);
			if (charge.getPaidMoney() >= charge.getChargeMoney() && !isQuery) {
				charge.setUnPaidMoney(0);
				charge.setPaidCompleted(true);
				charge.setChangeMoney(charge.getPaidMoney() - charge.getChargeMoney());
			} else {
				charge.setUnPaidMoney(charge.getChargeMoney() - charge.getPaidMoney());
			}
			if (isRealMonthUser) {
				// charge.setPaidMoney(0);
				charge.setChargeMoney(0);
				charge.setUnPaidMoney(0);
			}
			if (!isQuery) {
				this.update(charge);
			}

		}

	}
	//
	private void newFeeCalcExpense6(PosChargeData charge, FeeCriterion criterion, Date exitDate, Boolean isQuery,
			Park park) {
		// TODO Auto-generated method stub
		Date enterDate=charge.getEntranceDate();
		Date enterDateNew=new Date(enterDate.getTime()+criterion.getFreemins()*1000*60);
		long diff=(exitDate.getTime()-enterDateNew.getTime())/(1000*60);
		if (diff<=0) {
			charge.setChargeMoney(0);
			return ;
		}
		int leftMinuts=(int) (diff%(60*24));
		int days=(int) (diff/(60*24));
		double money=days*criterion.getMaxexpense();
		Date newEnterTime=new Date(exitDate.getTime()-leftMinuts*1000*60);
		//前三小时免费，3-5小时5元，5-12小时10元，12-24小时20元
		if (diff<=criterion.getStep1capacity()&&diff>0) {
			charge.setChargeMoney(criterion.getStep1price());
		}
		if (diff>criterion.getStep1capacity()&&diff<=criterion.getStep2capacity()) {
			charge.setChargeMoney(criterion.getStep2price());
		}
		if (diff>(criterion.getFreemins()+criterion.getStep1capacity()+criterion.getStep2capacity())) {
			charge.setChargeMoney(criterion.getOnetimeexpense());
		}
	}

	@Override
	public PosChargeData newFeeCalcExpenseWithData(PosChargeData charge, Date exitDate, Boolean isQuery, Park park,
			FeeCriterion criterion) throws Exception {
		if (exitDate == null) {
			exitDate = new Date();
		}
		charge.setExitDate1(exitDate);
		// 入场免费时间处理
		int freeMinutes = criterion.getFreemins();
		Date entranceDate = new Date(charge.getEntranceDate().getTime() + freeMinutes * 60 * 1000);
		String startTime = new SimpleDateFormat(Constants.DATEFORMAT).format(entranceDate);
		String endTime = new SimpleDateFormat(Constants.DATEFORMAT).format(exitDate);
		String nightStartHour = "20";
		String nightEndHour = "8";
		if (criterion.getNightstarttime() != null && criterion.getNightendtime() != null) {
			nightStartHour = criterion.getNightstarttime().split(":")[0];
			nightEndHour = criterion.getNightendtime().split(":")[0];
		}
		if (entranceDate.getTime() > exitDate.getTime()) {
			charge.setChargeMoney(0);
			return charge;
		}
		Map<String, String> dates = formatTime.format(startTime, endTime, nightStartHour, nightEndHour);
		double money = 0;
		double step1Price = criterion.getStep1price();
		double step2Price = criterion.getStep2price();
		int step1Minutes = 15;
		int step2minutes = 60;
		if (charge.getIsLargeCar()) {
			step1Price = criterion.getStep1pricelarge();
			step2Price = criterion.getStep2pricelarge();
		}
		for (String name : dates.keySet()) {
			logger.info("计费**车牌号:" + charge.getCardNumber() + "时间:" + name + "-" + dates.get(name));
			Date startDate = new SimpleDateFormat(Constants.DATEFORMAT).parse(name);
			Date endDate = new SimpleDateFormat(Constants.DATEFORMAT).parse(dates.get(name));
			String startd = name.substring(11, 13);
			if (Integer.parseInt(startd) >= Integer.parseInt(nightEndHour)
					&& Integer.parseInt(startd) < Integer.parseInt(nightStartHour)) {
				long diffs = endDate.getTime() - startDate.getTime();
				double hours = Math.ceil((double) diffs / (1000 * 60 * step1Minutes));
				money += hours * step1Price;
			} else {
				long diffs = endDate.getTime() - startDate.getTime();
				double hours = Math.ceil((double) diffs / (1000 * 60 * step2minutes));
				money += hours * step2Price;
			}
			logger.info("计费**车牌号:" + charge.getCardNumber() + "费用:" + money);
		}
		charge.setChargeMoney(money);
		return charge;
	}

	@Override
	public Map<String, Object> selectPosdataSumByParkAndRange(Date startDay, Date endDay, int parkId) {
		// TODO Auto-generated method stub
		return chargeDao.selectPosdataSumByParkAndRange(startDay, endDay, parkId);
	}

	@Override
	public Map<String, Object> calInByParkAndRange(int parkId, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return chargeDao.calInByParkAndRange(parkId, startDate, endDate);
	}
	
	@Override
	public Map<String, Object> calInByParkAndRangeMoney(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return chargeDao.calInByParkAndRangeMoney(startDate, endDate);
	}

	@Override
	public Map<String, Object> calOutByParkAndRange(int parkId, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return chargeDao.calOutByParkAndRange(parkId, startDate, endDate);
	}
	
	@Override
	public Map<String, Object> calOutByParkAndRangeMoney(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return chargeDao.calOutByParkAndRangeMoney(startDate, endDate);
	}

	/*@Override
	public int getByDateAndParkCount(int parkId, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return chargeDao.getByDateAndParkCount(parkId, startDate, endDate);
	}


	@Override
	public double getChannelCharge(int parkId, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return chargeDao.getChannelCharge(parkId, startDate, endDate);
	}*/

	@Override
	public String getByDateAndParkCountzbs(int parkId, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return chargeDao.getByDateAndParkCountzbs(parkId, startDate, endDate);
	}

	@Override
	public String getByDateAndParkCountzje(int parkId, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return chargeDao.getByDateAndParkCountzje(parkId, startDate, endDate);
	}

	@Override
	public String getByDateAndParkCountbs(int parkId, String startDate, String endDate, int payType) {
		// TODO Auto-generated method stub
		return chargeDao.getByDateAndParkCountbs(parkId, startDate, endDate, payType);
	}

	@Override
	public String getByDateAndParkCountje(int parkId, String startDate, String endDate, int payType) {
		// TODO Auto-generated method stub
		return chargeDao.getByDateAndParkCountje(parkId, startDate, endDate, payType);
	}

	@Override
	public void calExpense(PosChargeData charge, Date exitDate, Boolean isQuery) throws Exception {
		Boolean isMonthUser = false;
		Boolean isRealMonthUser = false;
		// Boolean isMonthUserMoreCars=false;
		List<Monthuser> monthusers = monthUserService.getByCarnumberAndPark(charge.getCardNumber(), charge.getParkId());
		Monthuser monthuserUse = new Monthuser();
		for (Monthuser monthuser : monthusers) {
			if (monthuser.getType() == 0) {
				Long diff = (monthuser.getEndtime().getTime() - (new Date()).getTime());
				if (diff > 0) {
					isMonthUser = true;
					monthuserUse = monthuser;
					isRealMonthUser = true;
					break;
				}
			}
		}
		Park park = parkService.getParkById(charge.getParkId());
		Boolean isMultiCarsOneCarport = false;
		if (park.getDescription() != null && park.getDescription().contains("一位多车")) {
			isMultiCarsOneCarport = true;
		}

		/*
		 * if (isRealMonthUser&&monthuserUse.getOwner()!=null&&!monthuserUse.
		 * getOwner().equals("")) { List<Monthuser>
		 * monthuserss=monthUserService.getByUsernameAndPark(monthuserUse.
		 * getOwner(), monthuserUse.getParkid()); List<Monthuser>
		 * realMonthUsers=new ArrayList<>(); for (Monthuser monthuser :
		 * monthuserss) { if (monthuser.getType().intValue()==0) {
		 * realMonthUsers.add(monthuser); } } if (realMonthUsers.size()>1) {
		 * isMonthUserMoreCars=true; for (Monthuser monthuser : realMonthUsers)
		 * { if (monthuser.getPlatecolor().equals("已入场")) {
		 * isRealMonthUser=false; break; } } } }
		 */

		if (isMultiCarsOneCarport&& monthuserUse.getPlatecolor() != null
				&& monthuserUse.getPlatecolor().equals("包月转临停")) {
			isRealMonthUser = false;
			monthuserUse.setPlatecolor("出场完结");
			monthUserService.updateByPrimaryKeySelective(monthuserUse);
		}
//		if (isMultiCarsOneCarport && isRealMonthUser && monthuserUse.getPlatecolor() != null
//				&& monthuserUse.getPlatecolor().equals("临停恢复为包月")) {
//			isRealMonthUser = false;
//			List<Monthuser> monthuserss = monthUserService.getByParkAndPort(monthuserUse.getParkid(),
//					monthuserUse.getCardnumber());
//
//			for (Monthuser monthuser : monthuserss) {
//				if (isMultiCarsOneCarport && isRealMonthUser && monthuserUse.getPlatecolor() != null
//						&& monthuser.getPlatecolor().contains("包月出场")) {
//					String[] datas = monthuser.getPlatecolor().split("#");
//					try {
//						Date dateout = new SimpleDateFormat(Constants.DATEFORMAT).parse(datas[1]);
//						charge.setExitDate1(dateout);// 更改出场时间为原先包月车的出场时间
//						exitDate = dateout;
//						charge.setRejectReason("临停转包月");
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//					monthuser.setPlatecolor("出场完结");
//					monthUserService.updateByPrimaryKeySelective(monthuser);
//					monthuserUse.setPlatecolor("出场完结");
//					for (Monthuser strMonthUser : monthuserss) {
//						if (strMonthUser.getPlatecolor().contains("包月转为临停")) {
//							monthuserUse.setPlatecolor(
//									"包月出场#" + new SimpleDateFormat(Constants.DATEFORMAT).format(new Date()));
//							strMonthUser.setPlatecolor("临停恢复为包月");
//							monthUserService.updateByPrimaryKeySelective(strMonthUser);
//							break;
//						}
//					}
//					monthUserService.updateByPrimaryKeySelective(monthuserUse);
//					break;
//				}
//			}
//
//		}
		if (isRealMonthUser) {
			charge.setChargeMoney(0);
			charge.setUnPaidMoney(0);
			charge.setExitDate1(exitDate);
			charge.setPaidCompleted(true);
			if (!isQuery) {
				if (isMultiCarsOneCarport && monthuserUse.getPlatecolor().equals("多车包月入场")) {
					monthuserUse.setPlatecolor("出场完结");
					List<Monthuser> monthuserss = monthUserService.getByUsernameAndPark(monthuserUse.getOwner(),
							monthuserUse.getParkid());
//					for (Monthuser monthuser : monthuserss) {
//						if (monthuser.getPlatecolor().equals("包月转为临停")) {
//							monthuser.setPlatecolor("临停恢复为包月");
//							monthuserUse.setPlatecolor(
//									"包月出场#" + new SimpleDateFormat(Constants.DATEFORMAT).format(new Date()));
//							monthUserService.updateByPrimaryKeySelective(monthuser);
//							break;
//						}
//					}
					monthUserService.updateByPrimaryKeySelective(monthuserUse);
				}
				this.update(charge);
			}
			return;
		}

		Integer criterionId = park.getFeeCriterionId();
		FeeCriterion criterion = criterionService.getById(criterionId);
		//白天晚上模版
		if (criterion.getType() == 2) {
			newFeeCalcExpense2(charge, criterion, exitDate, isQuery);
			if (isRealMonthUser) {
				charge.setChargeMoney(0);
				charge.setUnPaidMoney(0);
			}
			if (!isQuery) {
				this.update(charge);
			}
			return;
		}
		//按次收费
		if (criterion.getType() == 1) {
			newFeeCalcExpense1(charge, criterion, exitDate, isQuery);
			if (isRealMonthUser) {
				charge.setChargeMoney(0);
				charge.setUnPaidMoney(0);
			}
			if (!isQuery) {
				this.update(charge);
			}
			return;
		}
		
		if (criterion.getType() == 4) {//24小时
			newFeeCalcExpense4(charge, criterion, exitDate, isQuery,park);
			if (isRealMonthUser) {
				charge.setChargeMoney(0);
				charge.setUnPaidMoney(0);
			}
			if (!isQuery) {
				this.update(charge);
			}
			return;
		}
		//三个计费阶段
		if (criterion.getType()==6) {
			newFeeCalcExpense6(charge, criterion, exitDate, isQuery,park);
			if (isRealMonthUser) {
				charge.setChargeMoney(0);
				charge.setUnPaidMoney(0);
			}
			if (!isQuery) {
				this.update(charge);
			}
			return;
		}
		
		String startTime = new SimpleDateFormat(Constants.DATEFORMAT).format(charge.getEntranceDate());
		String endTime = new SimpleDateFormat(Constants.DATEFORMAT).format(exitDate);
		String nightStartHour = "20";
		String nightEndHour = "07";
		if (criterion.getNightstarttime() != null && criterion.getNightendtime() != null) {
			nightStartHour = criterion.getNightstarttime().split(":")[0];
			nightEndHour = criterion.getNightendtime().split(":")[0];
		}
		int isOneTimeExpense = charge.getIsOneTimeExpense();
		if (charge.getIsLargeCar() == false) {
			Map<String, String> dates = formatTime.format(startTime, endTime, nightStartHour, nightEndHour);
			for (String name : dates.keySet()) {
				charge.setEntranceDate(name);
				int houra = Integer.parseInt(name.substring(11, 13));
				int hourNight = Integer.parseInt(nightStartHour);
				int hourDay = Integer.parseInt(nightEndHour);
				boolean isNight = false;
				if (houra > 12) {
					if (houra >= hourNight) {
						isNight = true;
					}
				} else {
					if (houra < hourDay) {
						isNight = true;
					}
				}
				if (isNight) {
					charge.setIsOneTimeExpense(1);
					this.calExpenseSmallCar(charge, new SimpleDateFormat(Constants.DATEFORMAT).parse(dates.get(name)),
							isQuery);
				} else if (isOneTimeExpense == 0) {
					charge.setIsOneTimeExpense(0);
					this.calExpenseSmallCar(charge, new SimpleDateFormat(Constants.DATEFORMAT).parse(dates.get(name)),
							isQuery);
				} else {
					charge.setIsOneTimeExpense(1);
					this.calExpenseSmallCar(charge, new SimpleDateFormat(Constants.DATEFORMAT).parse(dates.get(name)),
							isQuery);
				}
			}
			// System.out.println("计费计算后: "+new Date().getTime()+"\n");
			// System.out.println("计费更新前: "+new Date().getTime()+"\n");
			charge.setEntranceDate(startTime);
			charge.setExitDate(endTime);
			if (charge.getPaidMoney() >= charge.getChargeMoney() && !isQuery) {
				charge.setUnPaidMoney(0);
				charge.setPaidCompleted(true);
				charge.setChangeMoney(charge.getPaidMoney() - charge.getChargeMoney());
			} else {
				charge.setUnPaidMoney(charge.getChargeMoney() - charge.getPaidMoney());
			}
			if (isRealMonthUser) {
				charge.setChargeMoney(0);
				charge.setUnPaidMoney(0);
			}
			if (!isQuery) {
				this.update(charge);
			}
			// System.out.println("计费更新后: "+new Date().getTime()+"\n");

		} else {
			Map<String, String> dates = formatTime.format(startTime, endTime, nightStartHour, nightEndHour);
			for (String name : dates.keySet()) {
				charge.setEntranceDate(name);
				int houra = Integer.parseInt(name.substring(11, 13));
				int hourNight = Integer.parseInt(nightStartHour);
				int hourDay = Integer.parseInt(nightEndHour);
				boolean isNight = false;
				if (houra > 12) {
					if (houra >= hourNight) {
						isNight = true;
					}
				} else {
					if (houra < hourDay) {
						isNight = true;
					}
				}
				if (isNight) {
					charge.setIsOneTimeExpense(1);
					this.calExpenseLargeCar(charge, new SimpleDateFormat(Constants.DATEFORMAT).parse(dates.get(name)),
							isQuery);
				} else if (isOneTimeExpense == 0) {
					charge.setIsOneTimeExpense(0);
					this.calExpenseLargeCar(charge, new SimpleDateFormat(Constants.DATEFORMAT).parse(dates.get(name)),
							isQuery);
				} else {
					charge.setIsOneTimeExpense(1);
					this.calExpenseLargeCar(charge, new SimpleDateFormat(Constants.DATEFORMAT).parse(dates.get(name)),
							isQuery);
				}
			}
			charge.setEntranceDate(startTime);
			charge.setExitDate(endTime);
			if (charge.getPaidMoney() >= charge.getChargeMoney() && !isQuery) {
				charge.setUnPaidMoney(0);
				charge.setPaidCompleted(true);
				charge.setChangeMoney(charge.getPaidMoney() - charge.getChargeMoney());
			} else {
				charge.setUnPaidMoney(charge.getChargeMoney() - charge.getPaidMoney());
			}
			if (isRealMonthUser) {
				// charge.setPaidMoney(0);
				charge.setChargeMoney(0);
				charge.setUnPaidMoney(0);
			}
			if (!isQuery) {
				this.update(charge);
			}

		}

		
	}

	@Override
	public List<Park> getParkByMoney(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return chargeDao.getParkByMoney(map);
	}

	@Override
	public int gongcount() {
		// TODO Auto-generated method stub
		return chargeDao.gongcount();
	}

	//主平台今日数据统计
	@Override
	public List<Park> getParkByCountMoney(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return chargeDao.getParkByCountMoney(map);
	}

	@Override
	public String getByDateAndParkCountPayzbs(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return chargeDao.getByDateAndParkCountPayzbs(startDate, endDate);
	}

	@Override
	public String getByDateAndParkCountPayzje(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return chargeDao.getByDateAndParkCountPayzje(startDate, endDate);
	}

	@Override
	public String getByDateAndParkCountPayTypebs(String startDate, String endDate, int payType) {
		// TODO Auto-generated method stub
		return chargeDao.getByDateAndParkCountPayTypebs(startDate, endDate, payType);
	}

	@Override
	public String getByDateAndParkCountPayTypeje(String startDate, String endDate, int payType) {
		// TODO Auto-generated method stub
		return chargeDao.getByDateAndParkCountPayTypeje(startDate, endDate, payType);
	}

	@Override
	public List<PosChargeData> getByCarNumberAndPN(String cardNumber, String parkName) {
		// TODO Auto-generated method stub
		return chargeDao.getByCarNumberAndPN(cardNumber, parkName);
	}

	@Override
	public List<PosChargeData> getByCardNumberAndParkName(String cardNumber, String parkName, Integer parkId,String operatorId) {
		// TODO Auto-generated method stub
		return chargeDao.getByCardNumberAndParkName(cardNumber, parkName, parkId,operatorId);
	}

	@Override
	public List<PosChargeData> getByParkDatetime(String cardNumber, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return chargeDao.getByParkDatetime(cardNumber, startDate, endDate);
	}

	@Override
	public String getByParkpaidMoneyjine(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return chargeDao.getByParkpaidMoneyjine(startDate, endDate);
	}
	
	@Override
	public String getBypaidMoneyjine(int parkId,String startDate, String endDate) {
		// TODO Auto-generated method stub
		return chargeDao.getBypaidMoneyjine(parkId, startDate, endDate);
	}

	@Override
	public List<PosChargeData> getDebtWithParkId(String cardNumber, Date exitDate, Integer parkId) throws Exception {
		// TODO Auto-generated method stub
		List<PosChargeData> charges = chargeDao.getDebtWithParkId(cardNumber, parkId);
		for (PosChargeData charge : charges) {
			if (charge.getExitDate() == null) {
				Park park = parkService.getParkById(charge.getParkId());
				if (park.getType() == 1) {
					this.calExpenseType1(charge, exitDate, false);
				} else {
					this.calExpense(charge, exitDate, false);
				}
			}
		}
		return charges;
	}
	
	//统计今日数据列表的应收实收
	@Override
	public Map<String, Object> selectPosdataSumByParkAndRangeMoney(int id,String startDay, String endDay) {
		// TODO Auto-generated method stub
		return chargeDao.selectPosdataSumByParkAndRangeMoney(id,startDay, endDay);
	}

}
