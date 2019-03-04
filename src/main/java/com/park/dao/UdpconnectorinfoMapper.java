package com.park.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.park.model.Udpconnectorinfo;

public interface UdpconnectorinfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Udpconnectorinfo record);

    int insertSelective(Udpconnectorinfo record);

    Udpconnectorinfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Udpconnectorinfo record);

    int updateByPrimaryKey(Udpconnectorinfo record);
    
    List<Udpconnectorinfo> getByMac(@Param("mac")String mac);
}