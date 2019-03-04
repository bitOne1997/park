package com.park.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.dao.CarportDAO;
import com.park.dao.CarportExpenseDao;
import com.park.model.Carport;
import com.park.model.CarportExpense;
import com.park.service.CarportService;

@Transactional
@Service
public class CarportServiceImpl implements CarportService{

	@Autowired
	private CarportDAO carportDAO;
	
	@Autowired
	private CarportExpenseDao expenseDAO;
	
	@Override
	public int insertCarport(Carport carportItem) {
		
		return carportDAO.insertCarport(carportItem);
	}
//
	@Override
	public Carport getCarportById(int id) {
		
		return carportDAO.getCarportById(id);
	}

	@Override
	public List<Carport> getCarports() {
		
		List<Carport> carports =  carportDAO.getCarports();
		return carports;
	}
	
	@Override
	public List<Carport> getSpecifyCarports(int start, int counts, String field, String order) {
		
		List<Carport> carports = carportDAO.getSpecifyCarports(start, counts, field, order);
		return carports;
	}
	@Override
	public List<Carport> getConditionCarports(int start, int counts,
			String field, String order, String queryCondition) {
		return carportDAO.getConditionCarports(start, counts, field, order, queryCondition);
		
	}
	
	private static double cal(String name, Date startTime, int type){
		
		double sum = 0;
		
		if(name.equals("学院路停车场") || name.equals("金盛路停车场")){
			int dayExpense = 6;
			int nightExpense = 5;
			if(type == 1){
				dayExpense = 8;
				nightExpense = 6;
			}
			
			int expense = 8;
			boolean isDayTime = true;
			Calendar c = Calendar.getInstance();
			Date endTime = c.getTime();
			if(startTime.getHours() >= 8 && startTime.getHours() <= 20)
				isDayTime = true;
			else
				isDayTime = false;
			
			while(startTime.before(endTime)){
				if(isDayTime)
					expense = dayExpense;
				else
					expense = nightExpense;
				sum += expense;
				isDayTime = !isDayTime;
				
				Calendar start = Calendar.getInstance();
				start.setTime(startTime);
				start.add(Calendar.HOUR_OF_DAY, 12);
				startTime = start.getTime();
			}
				
		}else if(name.equals("润和创智中心停车场")){
			Calendar c = Calendar.getInstance();
			Date endTime = c.getTime();
			long diffMins = (endTime.getTime() - startTime.getTime())/(1000 * 60);
			
			sum = diffMins / 15 * (type == 0? 0.5 : 1.5);
			
		}
		return sum;
	}
	
	@Override
	public double calExpense(String name, String carportNumber, String cardNumber, int type) {
		if(expenseDAO.count(name, carportNumber, cardNumber) > 0){
			CarportExpense expense = expenseDAO.get(name, carportNumber, cardNumber);
			expenseDAO.free(name, carportNumber, cardNumber);
			
			return this.cal(name, expense.getStartTime(), type);
		}else{
			expenseDAO.insert(name, carportNumber, cardNumber);
			return -1;
		}
			
	}

}
