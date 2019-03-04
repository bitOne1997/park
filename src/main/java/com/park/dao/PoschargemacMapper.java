package com.park.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.park.model.Poschargemac;

public interface PoschargemacMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Poschargemac record);

    int insertSelective(Poschargemac record);

    Poschargemac selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Poschargemac record);
    
    int updateByPosChargeId(Poschargemac record);

    int updateByPrimaryKey(Poschargemac record);
    
    List<Poschargemac> selectByMac(@Param("macId")int macId,@Param("count")int count);
}