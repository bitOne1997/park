package com.park.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;


import com.park.model.FeeCriterion;
import com.park.model.Monthuser;
import com.park.model.Outsideparkinfo;
import com.park.model.Park;
import com.park.model.Parktoalipark;
import com.park.model.PosChargeData;

public interface PosChargeDataService {
	public List<Park> getParkByCountMoney(Map<String, Object> map);
	
	//主平台
	//统计总笔数
	public String getByDateAndParkCountPayzbs(String startDate,String endDate);
	//统计总金额
	public String getByDateAndParkCountPayzje(String startDate,String endDate);
	//统计实收金额
	public String getByParkpaidMoneyjine(String startDate,String endDate);
	//统计各渠道笔数
	public String getByDateAndParkCountPayTypebs(String startDate,String endDate,int payType);
	//统计各渠道金额
	public String getByDateAndParkCountPayTypeje(String startDate,String endDate,int payType);
	
	public List<Park> getParkByMoney(Map<String, Object> map);
	
	//子平台
	//统计总笔数
	public String getByDateAndParkCountzbs(int parkId,String startDate,String endDate);
	//统计总金额
	public String getByDateAndParkCountzje(int parkId,String startDate,String endDate);
	//(主平台的)统计实收金额
	public String getBypaidMoneyjine(int parkId,String startDate,String endDate);
	//统计各渠道笔数
	public String getByDateAndParkCountbs(int parkId,String startDate,String endDate,int payType);
	//统计各渠道金额
	public String getByDateAndParkCountje(int parkId,String startDate,String endDate,int payType);
	
	public PosChargeData getById(int id);		
	
	public List<PosChargeData> get();
	
	public List<PosChargeData> getUnCompleted();
	
	public List<PosChargeData> getPage(int low, int count);
	
	public List<PosChargeData> getByParkIdAndCardNumber(Integer parkId,String cardNumber);
	
	public Integer deleteByParkIdAndDate(int parkId, String startDate,String endDate);
	
	public List<PosChargeData> getPageByParkId(int parkId,int start,int count);
	
	public List<PosChargeData> getByParkAuthority(String userName);
	
	public List<PosChargeData> getPageArrearage(int low, int count);
	
	public List<PosChargeData> getByCardNumberAndPark(String cardNumber,Integer parkId);
	
	public List<PosChargeData> getByCardNumberAndParkName(String cardNumber,String parkName, Integer parkId,String operatorId);
	
	public List<PosChargeData> getPageArrearageByParkId(int parkId,int start,int count);
	
	public List<PosChargeData> getByRange(int parkId,Date startDate,Date endDate);
	
	public List<PosChargeData> getAllByDay(String date) throws ParseException;
	
	public List<PosChargeData> getByParkAndDay(int parkId,String date) throws ParseException;
	
	public List<PosChargeData> getDebt (String cardNumber) throws Exception;
	
	public List<PosChargeData> getDebtWithData(String cardNumber,List<Parktoalipark> parktoaliparks,List<Monthuser> monthusers,Park park,Boolean isMultiFeeCtriterion,int carType) throws Exception;

	public void calExpenseSmallCarWithData(PosChargeData charge, Date exitDate,Boolean isQuery,Park park,FeeCriterion criterion );
	
	public void calExpenseLargeCarWithData(PosChargeData charge, Date exitDate,Boolean isQuery,Park park,FeeCriterion criterion );
	
	public List<PosChargeData> getLastRecord(String carNumber,int count);
	
	public List<PosChargeData> getLastRecordWithPark(String carNumber,int count,int parkId);
	
	public List<PosChargeData> getDebt (String cardNumber,Date exitDate) throws Exception;
	
	public List<PosChargeData> getArrearageByCardNumber(String cardNumber);
	
	public List<PosChargeData> queryDebt (String cardNumber,Date exitDate) throws Exception;
	
	public PosChargeData newFeeCalcExpense(PosChargeData charge, Date exitDate,Boolean isQuery) throws Exception;
	
	public PosChargeData newFeeCalcExpenseWithData(PosChargeData charge, Date exitDate,Boolean isQuery,Park park,FeeCriterion criterion) throws Exception;;
	
	public PosChargeData newFeeCalcExpense1(PosChargeData charge,FeeCriterion criterion, Date exitDate,Boolean isQuery) throws Exception;
	
	public PosChargeData newFeeCalcExpense2(PosChargeData charge, FeeCriterion criterion,Date exitDate,Boolean isQuery) throws Exception;
	
	public List<PosChargeData> queryCurrentDebt (String cardNumber,Date exitDate) throws Exception;
	
	public List<PosChargeData> getParkCarportStatusToday(int parkId,Date tmpdate);
	
	public int count();
	
	public int gongcount();
	
	public int deleteById(int id);
	
	public int insert(PosChargeData item);
	
	public int update(PosChargeData item);
		
	public PosChargeData pay(String cardNumber, double money) throws Exception;
	
	public PosChargeData updateRejectReason(String cardNumber,String rejectReason) throws Exception;
	
	public List<PosChargeData> repay(String cardNumber, double money) throws Exception;
	
	public void calExpense(PosChargeData charge, Date exitDate,Boolean isQuery) throws Exception;
	
	public void calExpensewithData(PosChargeData charge, Date exitDate,Boolean isQuery,List<Monthuser> monthusers,Park park,FeeCriterion criterion) throws Exception;
	
	public void calExpenseType1(PosChargeData charge, Date exitDate,Boolean isQuery) throws ParseException;
	
	public void calExpenseSmallCar(PosChargeData charge, Date exitDate,Boolean isQuery) throws Exception;
	
	public void calExpenseLargeCar(PosChargeData charge, Date exitDate,Boolean isQuery) throws Exception;
	
	public List<PosChargeData> selectPosdataByParkAndRange(Date startDay, Date endDay,int parkId);
	
	public List<PosChargeData> selectPosdataByParkAndRangeAndCarportNumber(Date startDay, Date endDay,int parkId,int carportNumber);
	
	public Map<String, Object> getParkChargeByDay(int parkId, String day);
	
	public Map<String, Object> selectPosdataSumByParkAndRange(Date startDay, Date endDay,int parkId);
	
	//统计今日数据列表的应收实收
	public Map<String, Object> selectPosdataSumByParkAndRangeMoney(int id,String startDay, String endDay);
	
	public Map<String, Object> getParkChargeCountByDay(int parkId, String day);
	
	public Map<String, Object> calInByParkAndRange(int parkId, Date startDate, Date endDate);
	
	//统计今日数据列表的应收实收
	public Map<String, Object> calInByParkAndRangeMoney(Date startDate, Date endDate);
	
	public Map<String, Object> calOutByParkAndRange(int parkId, Date startDate, Date endDate);
	
	//统计今日数据列表的应收实收
	public Map<String, Object> calOutByParkAndRangeMoney(Date startDate, Date endDate);
	
	public List<PosChargeData> getByCardNumber(String cardNumber);
	
	public List<PosChargeData> getByCarNumberAndPN(String cardNumber,String parkName);
	
	public List<PosChargeData> getByParkDatetime(String cardNumber,String startDate,String endDate);
	
	public List<PosChargeData> getByParkName(String parkName);
	
	public List<PosChargeData> hardwareRecord(Integer parkId,String startDate,String endDate) throws ParseException, Exception;
	
	public List<Map<String, Object>> getFeeOperatorChargeData(Date startDate,Date endDate);

	List<PosChargeData> getByParkAndDayRange(int parkId, String startDate,String endDate) throws ParseException;

	List<PosChargeData> getAllByDayRange(String startDate, String endDate) throws ParseException;
	
	public Outsideparkinfo getOutsideparkinfoByOrigin(int parkId,String day);
	
	//统计今日数据列表的应收实收
	public Outsideparkinfo getOutsideparkinfoByOriginMoney(int id,String day);

	List<PosChargeData> getCharges(String cardNumber) throws Exception;
	
	public List<PosChargeData> getByCardNumberAndPort(String cardNumber,Integer portNumber);
	void calExpenseMulti(PosChargeData charge, Date exitDate, Boolean isQuery, Boolean isMultiFeeCtriterion, int carType)
			throws Exception;
	PosChargeData payWithOperatorId(String cardNumber, double money, String operatorId) throws Exception;
	List<PosChargeData> queryDebtWithParkId(String cardNumber, Date exitDate, Integer parkId) throws Exception;
	List<PosChargeData> getDebtWithParkId(String cardNumber, Date exitDate, Integer parkId) throws Exception;
	PosChargeData newFeeCalcExpense4(PosChargeData charge, FeeCriterion criterion, Date exitDate, Boolean isQuery,
			Park park) throws Exception;
	
	

}
