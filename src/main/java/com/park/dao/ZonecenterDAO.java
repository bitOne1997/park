package com.park.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.park.model.Zonecenter;
@Repository
public interface ZonecenterDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(Zonecenter record);

    int insertSelective(Zonecenter record);

    Zonecenter selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Zonecenter record);

    int updateByPrimaryKey(Zonecenter record);
    
    int getCount();
    
    List<Zonecenter> getByStartAndCount(@Param("start")int start,@Param("count")int count);
}