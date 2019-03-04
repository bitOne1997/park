package com.park.model;

import java.util.Comparator;

import org.apache.poi.ss.formula.functions.T;

public class SingleParkInfoCompare implements Comparator<SingleParkInfo>{

	@Override
	public int compare(SingleParkInfo o1, SingleParkInfo o2) {
		// TODO Auto-generated method stub
		int flag=o1.getUpdatetime().compareTo(o2.getUpdatetime());
		return flag;
	}

}
