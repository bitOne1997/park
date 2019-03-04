package com.park.service;

import java.util.List;

import com.park.model.Repair;

public interface RepairService {
	List<Repair> getAll();

	int updateByPrimaryKey(Repair record);

	int insert(Repair record);
	int deleteByPrimaryKey(Integer id);
}
