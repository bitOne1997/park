package com.park.dao;

import com.park.model.Mobilelog;

public interface MobilelogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Mobilelog record);

    int insertSelective(Mobilelog record);

    Mobilelog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Mobilelog record);

    int updateByPrimaryKey(Mobilelog record);
}