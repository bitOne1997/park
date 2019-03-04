package com.park.dao;


import org.springframework.stereotype.Repository;

import com.park.model.ParkNews;

@Repository
public interface ParkNewsDAO {
	
	public ParkNews getLatestParkNews(int parkId);
	
	public int insertParkNews(ParkNews parkNews);

}
