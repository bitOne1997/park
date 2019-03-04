package com.park.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.dao.FeeoperatorDAO;
import com.park.model.Feeoperator;
import com.park.service.FeeOperatorService;
@Service
public class FeeOperatroServiceImpl implements FeeOperatorService {

	@Autowired
	private FeeoperatorDAO feeoperatroDao;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return feeoperatroDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Feeoperator record) {
		// TODO Auto-generated method stub
		return feeoperatroDao.insert(record);
	}

	@Override
	public int insertSelective(Feeoperator record) {
		// TODO Auto-generated method stub
		return feeoperatroDao.insertSelective(record);
	}

	@Override
	public Feeoperator selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return feeoperatroDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Feeoperator record) {
		// TODO Auto-generated method stub
		return feeoperatroDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Feeoperator record) {
		// TODO Auto-generated method stub
		return feeoperatroDao.updateByPrimaryKey(record);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return feeoperatroDao.getCount();
	}

	@Override
	public List<Feeoperator> getByStartAndCount(int start, int count) {
		// TODO Auto-generated method stub
		return feeoperatroDao.getByStartAndCount(start, count);
	}

	@Override
	public List<Feeoperator> operatorValidation(String account, String passwd) {
		// TODO Auto-generated method stub
		return feeoperatroDao.operatorValidation(account, passwd);
	}

	@Override
	public List<Feeoperator> getByNameAndPhoneParkName(String name, String phone, String parkName) {
		// TODO Auto-generated method stub
		return feeoperatroDao.getByNameAndPhoneParkName(name, phone, parkName);
	}

	@Override
	public void operatorsLogout() {
		// TODO Auto-generated method stub
		List<Feeoperator> feeoperators=feeoperatroDao.getByStartAndCount(0, 2000);
		for (Feeoperator feeoperator : feeoperators) {
			if (feeoperator.getSignstatus()==true) {
				feeoperator.setSignstatus(false);
				updateByPrimaryKeySelective(feeoperator);
			}
		}
	}

	@Override
	public List<Feeoperator> getOperatorByAccount(String account) {
		// TODO Auto-generated method stub
		return feeoperatroDao.getOperatorByAccount(account);
	}


}
