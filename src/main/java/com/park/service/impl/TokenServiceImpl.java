package com.park.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.dao.TokenDAO;
import com.park.model.Token;
import com.park.service.TokenService;
import com.park.service.Utility;

@Transactional
@Service
public class TokenServiceImpl implements TokenService{
	
	@Autowired
	private TokenDAO tokenDAO;

	@Override
	public boolean validToken(String token) {
		if(tokenDAO.getTokenCount(token) > 0)
			return true;
		return false;
	}
	
	public int getTokenId(String token){
		return tokenDAO.getTokenId(token);
	}

	@Override
	public int insertToken() {
		Token token = new Token();
		token.setToken(Utility.createToken());
		int ret = tokenDAO.insert(token);
		if(ret == 0)
			return ret;
		else
			return tokenDAO.getTokenId(token.getToken());
		
	}
	
	public String getToken(int id){
		return tokenDAO.getToken(id);
	}

}
