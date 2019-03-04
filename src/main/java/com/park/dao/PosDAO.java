package com.park.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.park.model.Pos;
@Repository
public interface PosDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(Pos record);

    int insertSelective(Pos record);

    Pos selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Pos record);

    int updateByPrimaryKey(Pos record);
    
    int getCount();
    
    List<Pos> getByStartAndCount(@Param("start")int start,@Param("count")int count);
    
    List<Pos> getByNum(@Param("num")String num);
    
    List<Pos> getByParkId(@Param("parkId")Integer parkId);
    
    List<Pos> getByParkNameAndNumber(@Param("parkName")String parkName,@Param("num")String num);
}