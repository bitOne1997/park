package com.park.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.park.model.Merchant;

@Repository
public interface MerchantDAO {
	
	public List<Merchant> getMerchants();
	
	public Merchant getMerchantById(int id);
	
	public int getMerchantCount();
	
	public int insertMerchant(Merchant merchant);
	
	public int updateMerchant(Merchant merchant);
	
	public int deleteMerchant(int id);

}
