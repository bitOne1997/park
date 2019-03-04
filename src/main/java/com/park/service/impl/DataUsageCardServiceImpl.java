package com.park.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.dao.DataUsageCardDAO;
import com.park.model.DataUsageCard;
import com.park.model.DataUsageCardDetail;
import com.park.service.DataUsageCardService;


@Transactional
@Service
public class DataUsageCardServiceImpl implements DataUsageCardService{

	@Autowired
	private DataUsageCardDAO cardDAO;
	
	@Override
	public int getCardCount() {
		return cardDAO.getCardCount();
	}
	
	@Override
	public List<DataUsageCardDetail> getCardsByParkId(int parkId) {
		return cardDAO.getCardsByParkId(parkId);
	}

	@Override
	public List<DataUsageCardDetail> getCardDetail(int low, int count) {
		return cardDAO.getCardDetail(low, count);
	}

	@Override
	public List<DataUsageCardDetail> getCardById(int id) {
		return cardDAO.getCardById(id);
	}

	@Override
	public List<DataUsageCard> getUsageCardById(int id){
		return cardDAO.getUsageCardById(id);
	}
	
	@Override
	public List<DataUsageCardDetail> getCardByCardNumber(String cardNumber) {
		return cardDAO.getCardByCardNumber(cardNumber);
	}

	@Override
	public List<DataUsageCardDetail> getCardByPhoneNumber(String phoneNumber) {
		return cardDAO.getCardByPhoneNumber(phoneNumber);
	}

	@Override
	public int insertCard(DataUsageCard card) {
		return cardDAO.insertCard(card);
	}

	@Override
	public int updateCard(DataUsageCard card) {
		return cardDAO.updateCard(card);
	}

	@Override
	public int deleteCard(int id) {
		return cardDAO.deleteCard(id);
	}

	@Override
	public void fillUsage(List<DataUsageCard> cards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fillDetailUsage(
			List<DataUsageCardDetail> cards) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<DataUsageCardDetail> getCardDetailByKeywords(String keyWords) {
		// TODO Auto-generated method stub
		return cardDAO.getCardDetailByKeywords(keyWords);
	}

	@Override
	public List<DataUsageCardDetail> getAllCardDetail() {
		// TODO Auto-generated method stub
		return cardDAO.getAllCardDetail();
	}

}
