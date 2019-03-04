package com.park.service;

import java.util.List;

import com.park.model.Pos;

public interface PosService {
	int deleteByPrimaryKey(Integer id);

    int insert(Pos record);

    int insertSelective(Pos record);

    Pos selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Pos record);

    int updateByPrimaryKey(Pos record);
    
    int getCount();
    
    List<Pos> getByStartAndCount(int start,int count);
    
    List<Pos> getByNum(String num);
    
    List<Pos> getByParkId(Integer parkId);
    
    List<Pos> getByParkNameAndNumber(String parkName,String num);
}
