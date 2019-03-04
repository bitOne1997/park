package com.park.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.park.model.DataUsageCard;
import com.park.model.DataUsageCardDetail;

@Repository
public interface DataUsageCardDAO {
	
	public int getCardCount();
	
	public List<DataUsageCardDetail> getCardDetail(int low, int count);
	
	public List<DataUsageCardDetail> getAllCardDetail();
	
	public List<DataUsageCardDetail> getCardsByParkId(int parkId);
	
	public List<DataUsageCardDetail> getCardById(int id);
	
	public List<DataUsageCard> getUsageCardById(int id);
	
	public List<DataUsageCardDetail> getCardByCardNumber(String cardNumber);
	
	public List<DataUsageCardDetail> getCardByPhoneNumber(String phoneNumber);
	
	public List<DataUsageCardDetail> getCardDetailByKeywords(@Param("param")String keyWords);
	
	public int insertCard(DataUsageCard card);
	
	public int updateCard(DataUsageCard card);
	
	public int deleteCard(int id);
	

}
