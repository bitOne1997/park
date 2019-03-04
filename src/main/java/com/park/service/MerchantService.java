package com.park.service;

import java.util.List;

import com.park.model.Merchant;

public interface MerchantService {
	
	public List<Merchant> getMerchants();
	
	public Merchant getMerchantById(int id);
	
	public List<Merchant> getNearMerchant(double longitude, double latitude, double radius);
	
	public int getMerchantCount();
	
	public int insertMerchant(Merchant merchant);
	
	public int updateMerchant(Merchant merchant);
	
	public int deleteMerchant(int id);

}
