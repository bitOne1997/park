package com.park.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.park.model.Monthuser;
@Repository
public interface MonthuserDAO {
	
    int deleteByPrimaryKey(Integer id);

    int insert(Monthuser record);
    
    Monthuser getByPlateNumberById(@Param("id")int id);
    
    Monthuser selectById(@Param("parkId")int parkId,@Param("type")int type,@Param("owner")String owner,@Param("starttime")Date starttime,@Param("endtime")Date endtime,@Param("platenumber")String platenumber );
    
    int insertSelective(Monthuser record);

    Monthuser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Monthuser record);

    int updateByPrimaryKey(Monthuser record);
    
    int getCount();
    
    int getCountByParkId(@Param("parkId")int parkId);
    
    List<Monthuser> getByPlateNumberBytype(@Param("platenumber")String platenumber,@Param("type")int type, @Param("owner")String owner,@Param("certificatetype")String certificatetype);
    
    List<Monthuser> getByPlateNumberAndParkId(@Param("parkId")int parkId,@Param("platenumber")String platenumber);
    
    List<Monthuser> getByStartAndCount(@Param("start")int start,@Param("count")int count);
    
    List<Monthuser> getByUsernameAndPark(@Param("username")String username,@Param("parkId")int parkId);
    
    List<Monthuser> getByParkAndPort(@Param("parkId")int parkId,@Param("portNumber")String portNumber);
    
    List<Monthuser> getByUserNameAndParkAndPort(@Param("username")String username,@Param("parkId")int parkId,@Param("portNumber")String portNumber);
    
    List<Monthuser> getByCarnumberAndPark(@Param("carnumber")String carnumber,@Param("parkId")int parkId);
    
    List<Monthuser> getLast3Number(@Param("lastNumber")String lastNumber,@Param("parkId")int parkId);
    
    List<Monthuser> getByStartAndCountAndOrder(@Param("start")int start,@Param("count")int count,@Param("type")int type);
    
    List<Monthuser> getByCardNumber(@Param("cardNumber")String cardNumber);
    
    List<Monthuser> getByParkIdAndCount(@Param("parkId")int parkId,@Param("start")int start,@Param("count")int count);
    
    List<Monthuser> getByParkIdAndCountOrder(@Param("parkId")int parkId,@Param("start")int start,@Param("count")int count,@Param("type")int type);
    
    Map<String, Object> statisticsInfo(@Param("parkId")int parkId,@Param("type")int type);
    
    List<Map<String, Object>> getMonthuserCountsByDateRangeAndPark(@Param("parkId")int parkId,@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("maxCount")int maxCount);
}