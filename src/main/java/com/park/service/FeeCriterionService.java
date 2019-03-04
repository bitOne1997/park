package com.park.service;

import java.util.List;

import com.park.model.FeeCriterion;
public interface FeeCriterionService {

	List<FeeCriterion> get();

	int insert(FeeCriterion criterion);

	int modify(FeeCriterion criterion);

	int delete(int id);

	FeeCriterion getById(Integer criterionId);
	
	List<FeeCriterion> getByKeyWords(String keyWords);
	
	List<FeeCriterion> operatorValidationid(int id);
}
