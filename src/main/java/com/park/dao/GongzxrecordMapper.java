package com.park.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.park.model.GongzxRecord;
import com.park.model.Gongzxrecord2;

public interface GongzxrecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Gongzxrecord2 record);

    int insertSelective(Gongzxrecord2 record);

    Gongzxrecord2 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Gongzxrecord2 record);

    int updateByPrimaryKey(Gongzxrecord2 record);
    
    List<Gongzxrecord2> selectByTradeNumber(@Param("tradeNumber")String tradeNumber);
}