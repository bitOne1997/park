package com.park.dao;

import org.springframework.stereotype.Repository;

import com.park.model.CarportExpense;

@Repository
public interface CarportExpenseDao {
	
	public int count(String parkName, String carportNumber, String cardNumber);
	
	public int free(String parkName, String carportNumber, String cardNumber);
	
	public int insert(String parkName, String carportNumber, String cardNumber);

	public CarportExpense get(String name, String carportNumber, String cardNumber);

}
