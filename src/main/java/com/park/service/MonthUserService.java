package com.park.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.park.model.Monthuser;

public interface  MonthUserService {

	    int deleteByPrimaryKey(Integer id);

	    int insert(Monthuser record);
	    
	    Monthuser getByPlateNumberById(int id);
	    
	    Monthuser selectById(int parkId,int type,String owner,Date starttime,Date endtime,String platenumber );

	    int insertSelective(Monthuser record);

	    Monthuser selectByPrimaryKey(Integer id);
	    
	    int updateByPrimaryKey(Monthuser record);
	    
	    int updateByPrimaryKeySelective(Monthuser record);
	    
	    int getCount();
	    
	    int getCountByParkId(int parkId);
	    
	    List<Monthuser> getByPlateNumberAndParkId(int parkId,String platenumber);
	    
	    List<Monthuser> getByPlateNumberBytype(String platenumber,int type,String owner,String certificatetype);
	    
	    List<Monthuser> getByStartAndCount(int start,int count);
	    
	    List<Monthuser> getByStartAndCountOrder(int start,int count,int type);
	    
	    List<Monthuser> getByUsernameAndPark(String username,int parkId);
	    
	    List<Monthuser> getByParkAndPort(int parkId,String portNumber);
	    
	    List<Monthuser> getByUserNameAndParkAndPort(String username,int parkId,String portNumber);
	    
	    List<Monthuser> getByCarnumberAndPark(String carnumber,int parkId);
	    
	    List<Monthuser> getByCardNumber(String cardNumber);
	    
	    List<Monthuser> getLast3Number(String lastNumber,int parkId);
	    
	    List<Monthuser> getByParkIdAndCount(int parkId,int start,int count);
	    
	    List<Monthuser> getByParkIdAndCountOrder(int parkId,int start,int count,int type);
	   
	    Map<String, Object> statisticsInfo(int parkId,int type);
	    
	    List<Map<String, Object>> getMonthuserCountsByDateRangeAndPark(int parkId,Date startDate,Date endDate,int maxCount);

}
