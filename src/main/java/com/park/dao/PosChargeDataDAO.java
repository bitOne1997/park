package com.park.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.park.model.PosChargeData;
import com.park.model.Park;

@Repository
public interface PosChargeDataDAO {
	
	public List<Park> getParkByCountMoney(Map<String, Object> map);
	
	//主平台
	//统计总笔数
	public String getByDateAndParkCountPayzbs(@Param("startDate")String startDate,@Param("endDate")String endDate);
	//统计总金额
	public String getByDateAndParkCountPayzje(@Param("startDate")String startDate,@Param("endDate")String endDate);
	//统计实收金额
	public String getByParkpaidMoneyjine(@Param("startDate")String startDate,@Param("endDate")String endDate);
	//统计各渠道笔数
	public String getByDateAndParkCountPayTypebs(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("payType")int payType);
	//统计各渠道金额
	public String getByDateAndParkCountPayTypeje(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("payType")int payType);
	//统计今日数据列表的应收实收
	public Map<String, Object> selectPosdataSumByParkAndRangeMoney(@Param("id")int id,@Param("startDay") String startDay, @Param("endDay") String endDay);
	
	public List<Park> getParkByMoney(Map<String, Object> map);
	
	//子平台
	//统计总笔数
	public String getByDateAndParkCountzbs(@Param("parkId")int parkId,@Param("startDate")String startDate,@Param("endDate")String endDate);
	//统计总金额
	public String getByDateAndParkCountzje(@Param("parkId")int parkId,@Param("startDate")String startDate,@Param("endDate")String endDate);
	//(主平台的)统计实收金额
	public String getBypaidMoneyjine(@Param("parkId")int parkId,@Param("startDate")String startDate,@Param("endDate")String endDate);
	//统计各渠道笔数
	public String getByDateAndParkCountbs(@Param("parkId")int parkId,@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("payType")int payType);
	//统计各渠道金额
	public String getByDateAndParkCountje(@Param("parkId")int parkId,@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("payType")int payType);
	
	public PosChargeData getById(int id);

	public List<PosChargeData> get();
	
	public List<PosChargeData> getPage(int low, int count);
	
	public List<PosChargeData> getByParkIdAndCardNumber(@Param("parkId")int parkId,@Param("cardNumber")String cardNumber);
	
	public List<PosChargeData> getByCardNumberAndPark(@Param("cardNumber")String cardNumber,@Param("parkId")int parkId);
	
	public List<PosChargeData> getByCardNumberAndParkName(@Param("cardNumber")String cardNumber,@Param("parkName")String parkName,@Param("parkId")int parkId,@Param("operatorId")String operatorId);
	
	public List<PosChargeData> getPageByParkId(@Param("parkId")int parkId,@Param("start")int start,@Param("count")int count);
	
	public List<PosChargeData> getPageArrearage(int low, int count);
	
	public List<PosChargeData> getPageArrearageByParkId(@Param("parkId")int parkId,@Param("start")int start,@Param("count")int count);
	
	public List<PosChargeData> getByRange(@Param("parkId")int parkId,@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	public List<PosChargeData> getAllByDay(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	public List<PosChargeData> getParkCarportStatusToday(@Param("parkId")int parkId,@Param("day")String day);
	
	public int count();
	
	public int gongcount();
	
	public int deleteById(@Param("id")int id);
	
	public int insert(PosChargeData item);
	
	public int update(PosChargeData item);
	
	public List<PosChargeData> getDebt(String cardNumber);
	
	public List<PosChargeData> getDebtWithParkId(@Param("carNumber")String carNumber,@Param("parkId")int parkId);

	public List<PosChargeData> getLastRecord(@Param("carNumber")String carNumber,@Param("count")int count);
	
	public List<PosChargeData> getLastRecordWithPark(@Param("carNumber")String carNumber,@Param("count")int count,@Param("parkId")int parkId);
	
	public List<PosChargeData> getArrearageByCardNumber(String cardNumber);

	public List<PosChargeData> getUnCompleted();
	
	public List<PosChargeData> selectPosdataByParkAndRangeAndCarportNumber(@Param("startDay") Date startDay, @Param("endDay") Date endDay,@Param("parkId")int parkId,@Param("carportNumber")int carportNumber);
	
	public List<PosChargeData> selectPosdataByParkAndRange(@Param("startDay") Date startDay, @Param("endDay") Date endDay,@Param("parkId")int parkId);
	
	public int deleteByParkIdAndDate(@Param("startDay") String startDay, @Param("endDay") String endDay,@Param("parkId")int parkId);
	
	public List<PosChargeData> getByCardNumber(@Param("cardNumber")String cardNumber);
	
	public List<PosChargeData> getByCardNumberAndPort(@Param("cardNumber")String cardNumber,@Param("portNumber")Integer portNumber);
	
	public List<PosChargeData> getByParkName(@Param("parkName")String parkName);
	
	public List<PosChargeData> getByCarNumberAndPN(@Param("cardNumber")String cardNumber,@Param("parkName")String parkName);
	
	public List<PosChargeData> getByParkDatetime(@Param("cardNumber")String cardNumber,@Param("startDate")String startDate,@Param("endDate")String endDate);
	
	public Map<String, Object> calInByParkAndRange(@Param("parkId")int parkId,@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	//统计今日数据列表的应收实收
	public Map<String, Object> calInByParkAndRangeMoney(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	public Map<String, Object> calOutByParkAndRange(@Param("parkId")int parkId,@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	//统计今日数据列表的应收实收
	public Map<String, Object> calOutByParkAndRangeMoney(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	public Map<String, Object> selectPosdataSumByParkAndRange(@Param("startDay") Date startDay, @Param("endDay") Date endDay,@Param("parkId")int parkId);
	
	public List<Map<String, Object>> getFeeOperatorChargeData(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
