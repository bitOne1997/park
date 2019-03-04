package com.park.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.park.model.Feeoperator;

public interface FeeoperatorDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(Feeoperator record);

    int insertSelective(Feeoperator record);

    Feeoperator selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Feeoperator record);

    int updateByPrimaryKey(Feeoperator record);
    
    int getCount();
    
    List<Feeoperator> getByStartAndCount(@Param("start")int start,@Param("count")int count);
    
    List<Feeoperator> operatorValidation(@Param("account")String account,@Param("passwd")String passwd);
    
    List<Feeoperator> getOperatorByAccount(@Param("account")String account);
    
    List<Feeoperator> getByNameAndPhoneParkName(@Param("name")String name,@Param("phone")String phone,@Param("parkName")String parkName);
    }