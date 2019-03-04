package com.park.service;
import java.util.List;

import com.park.model.Carport;

public interface CarportService {
	
	/**
	 * get carport by id
	 * @param id
	 * @return
	 */
	public Carport getCarportById(int id);
	
	/**
	 * get all carports
	 * @return
	 */
	List<Carport> getCarports();
	
	List<Carport> getSpecifyCarports(int start, int counts, String field, String order);
	
	List<Carport> getConditionCarports(int start, int counts, String field, String order, String queryCondition);
	
	/**
	 * 添加车位
	 * @param carport
	 * @return 
	 */	
	public int insertCarport(Carport carportItem);
	
	public double calExpense(String name, String carportNumber, String cardNumber, int type);
}
