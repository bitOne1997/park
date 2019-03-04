package com.park.service;

import java.util.List;

import com.park.model.DataUsageCard;
import com.park.model.DataUsageCardDetail;

public interface DataUsageCardService {
	
	public int getCardCount();
	
	public List<DataUsageCardDetail> getCardDetail(int low, int count);
	
	public List<DataUsageCardDetail> getAllCardDetail();
	
	public List<DataUsageCardDetail> getCardsByParkId(int parkId);
	
	public List<DataUsageCardDetail> getCardById(int id);
	
	public List<DataUsageCard> getUsageCardById(int id);
	
	public void fillUsage(List<DataUsageCard> cards);
	
	public void fillDetailUsage(List<DataUsageCardDetail> cards);
	
	public List<DataUsageCardDetail> getCardByCardNumber(String cardNumber);
	
	public List<DataUsageCardDetail> getCardByPhoneNumber(String phoneNumber);
	
	public List<DataUsageCardDetail> getCardDetailByKeywords(String keyWords);
	
	public int insertCard(DataUsageCard card);
	
	public int updateCard(DataUsageCard card);
	
	public int deleteCard(int id);

}
