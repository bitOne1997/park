package com.park.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.dao.MerchantDAO;
import com.park.model.Merchant;
import com.park.service.MerchantService;
import com.park.service.Utility;

@Transactional
@Service
public class MerchantServiceImpl implements MerchantService {

	@Autowired
	private MerchantDAO merchantDAO;
	
	@Override
	public List<Merchant> getMerchants() {
		// TODO Auto-generated method stub
		return merchantDAO.getMerchants();
	}

	@Override
	public Merchant getMerchantById(int id) {
		// TODO Auto-generated method stub
		return merchantDAO.getMerchantById(id);
	}

	@Override
	public List<Merchant> getNearMerchant(double longitude, double latitude,
			double radius) {
		List<Merchant> merchants = this.getMerchants();
		List<Merchant> nearMerchants = new ArrayList<Merchant>();
		for(Merchant merchant : merchants){
			double distance = Utility.GetDistance(latitude, longitude, merchant.getLatitude(), merchant.getLongitude());
			if(distance < radius)
				nearMerchants.add(merchant);
		}
		
		return nearMerchants;
	}
	
	@Override
	public int getMerchantCount() {
		// TODO Auto-generated method stub
		return merchantDAO.getMerchantCount();
	}

	@Override
	public int insertMerchant(Merchant merchant) {
		// TODO Auto-generated method stub
		return merchantDAO.insertMerchant(merchant);
	}

	@Override
	public int updateMerchant(Merchant merchant) {
		// TODO Auto-generated method stub
		return merchantDAO.updateMerchant(merchant);
	}

	@Override
	public int deleteMerchant(int id) {
		if(merchantDAO.getMerchantById(id) == null)
			return 1;
		return merchantDAO.deleteMerchant(id);
	}

	

}
