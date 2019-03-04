package com.park.dao;

import org.apache.ibatis.annotations.Param;

import com.park.model.ChargedataPark;
import com.park.model.ChargedataParkWithTable;

public interface ChargedataParkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargedataPark record);

    int insertSelective(ChargedataPark record);

    ChargedataPark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChargedataPark record);

    int updateByPrimaryKey(ChargedataPark record);
    
    int insertTable(ChargedataParkWithTable record);
    
    int insertSelectiveTable(ChargedataParkWithTable record);
    
    int updateByPrimaryKeyTable(ChargedataParkWithTable record);
    
    void generateTable(@Param("tableName")String tableName);
}