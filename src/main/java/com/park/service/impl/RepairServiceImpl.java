package com.park.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.dao.RepairDAO;
import com.park.model.Repair;
import com.park.service.RepairService;
@Transactional
@Service
public class RepairServiceImpl implements RepairService {
	@Autowired
	private RepairDAO repair;
	@Override
	public List<Repair> getAll() {
		// TODO Auto-generated method stub
		return repair.getAll();
	}

	@Override
	public int updateByPrimaryKey(Repair record) {
		// TODO Auto-generated method stub
		return repair.updateByPrimaryKey(record);
	}

	@Override
	public int insert(Repair record) {
		// TODO Auto-generated method stub
		return repair.insert(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return repair.deleteByPrimaryKey(id);
	}

}
