package com.park.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.dao.ChargedataParkMapper;
import com.park.model.ChargedataPark;
import com.park.model.ChargedataParkWithTable;
import com.park.service.ChargeDataService;
@Service
public class ChargeDataServiceImpl implements ChargeDataService {

	@Autowired
	ChargedataParkMapper chargedataparkMapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return chargedataparkMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ChargedataPark record) {
		// TODO Auto-generated method stub
		return chargedataparkMapper.insert(record);
	}

	@Override
	public int insertSelective(ChargedataPark record) {
		// TODO Auto-generated method stub
		return chargedataparkMapper.insertSelective(record);
	}

	@Override
	public ChargedataPark selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return chargedataparkMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ChargedataPark record) {
		// TODO Auto-generated method stub
		return chargedataparkMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ChargedataPark record) {
		// TODO Auto-generated method stub
		return chargedataparkMapper.updateByPrimaryKey(record);
	}

	@Override
	public void generateTable(int parkId) {
		// TODO Auto-generated method stub
		String tableName="chargedata_"+parkId;
		chargedataparkMapper.generateTable(tableName);
	}

	@Override
	public int insertTable(ChargedataParkWithTable record) {
		// TODO Auto-generated method stub
		int num=0;
		try {
			num=chargedataparkMapper.insertSelectiveTable(record);
		} catch (Exception e) {
			// TODO: handle exception
			generateTable(record.getParkid());
			num=chargedataparkMapper.insertSelectiveTable(record);
		}
		return num;
	}

	@Override
	public int updateByPrimaryKeyTable(ChargedataParkWithTable record) {
		// TODO Auto-generated method stub
		return chargedataparkMapper.updateByPrimaryKeyTable(record);
	}

	@Override
	public int insertSelectiveTable(ChargedataParkWithTable record) {
		// TODO Auto-generated method stub
		return chargedataparkMapper.insertSelectiveTable(record);
	}

}
