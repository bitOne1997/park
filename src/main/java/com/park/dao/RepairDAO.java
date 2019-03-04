package com.park.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.park.model.Repair;
@Repository
public interface RepairDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(Repair record);

    int insertSelective(Repair record);

    Repair selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Repair record);

    int updateByPrimaryKey(Repair record);
    
    List<Repair> getAll();
}