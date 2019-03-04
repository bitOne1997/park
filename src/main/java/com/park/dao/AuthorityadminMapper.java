package com.park.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.park.model.Authorityadmin;
import com.qiniu.util.Auth;

public interface AuthorityadminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Authorityadmin record);

    int insertSelective(Authorityadmin record);

    Authorityadmin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Authorityadmin record);

    int updateByPrimaryKey(Authorityadmin record);
    
    List<Authorityadmin> getByToken(@Param("token") String token);
    
    List<Authorityadmin> getAll();
}