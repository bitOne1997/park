package com.park.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.dao.FeeCriterionDAO;
import com.park.model.FeeCriterion;
import com.park.service.FeeCriterionService;

@Transactional
@Service
public class FeeCriterionServiceImpl implements FeeCriterionService{
	
	@Autowired
	FeeCriterionDAO criterionDAO;

	@Override
	public List<FeeCriterion> get() {
		return criterionDAO.get();
	}

	@Override
	public int insert(FeeCriterion criterion) {
		return criterionDAO.insert(criterion);
	}

	@Override
	public int modify(FeeCriterion criterion) {
		return criterionDAO.modify(criterion);
	}

	@Override
	public int delete(int id) {
		return criterionDAO.delete(id);
	}

	@Override
	public FeeCriterion getById(Integer criterionId) {
		return criterionDAO.getById(criterionId);
	}

	@Override
	public List<FeeCriterion> getByKeyWords(String keyWords) {
		// TODO Auto-generated method stub
		return criterionDAO.getByKeyWords(keyWords);
	}

	@Override
	public List<FeeCriterion> operatorValidationid(int id) {
		// TODO Auto-generated method stub
		return criterionDAO.operatorValidationid(id);
	}
	
	

}
