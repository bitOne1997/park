package com.park.service;

import java.util.List;

import com.park.model.Udpconnectorinfo;
import com.park.model.Udpconnectors;

public interface UdpConnectorInfoService {

	int deleteByPrimaryKey(Integer id);

    int insert(Udpconnectorinfo record);

    int insertSelective(Udpconnectorinfo record);

    Udpconnectorinfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Udpconnectorinfo record);

    int updateByPrimaryKey(Udpconnectorinfo record);
    
    List<Udpconnectorinfo> getByMac(String mac);
    
    void handleUdpConnect(Udpconnectors udpconnector,String mac);
    
}
