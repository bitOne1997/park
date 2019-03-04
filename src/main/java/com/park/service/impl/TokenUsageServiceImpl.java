package com.park.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.dao.TokenUsageDAO;
import com.park.model.TokenUsage;
import com.park.model.UserTokenUsage;
import com.park.service.TokenService;
import com.park.service.TokenUsageService;

@Transactional
@Service
public class TokenUsageServiceImpl implements TokenUsageService{

	@Autowired
	TokenService tokenService;
	
	@Autowired
	TokenUsageDAO tokenUsageDao;
	
	@Override
	public int count() {
		return tokenUsageDao.count();
	}

	@Override
	public List<UserTokenUsage> get(int begin, int len) {
		List<UserTokenUsage> ret = tokenUsageDao.get(begin, len);
		return ret != null ? ret : new ArrayList<UserTokenUsage>();
	}

	@Override
	public List<UserTokenUsage> getUsage(int userId) {
		List<UserTokenUsage> ret = tokenUsageDao.getUsage(userId);
		return ret != null ? ret : new ArrayList<UserTokenUsage>();
	}

	@Override
	public int insert(TokenUsage usage) {
		return tokenUsageDao.insertUsage(usage);
	}
	
	@Override
	public int insertRecord(String token, String url){
		
		if(url.contains("access"))
			return 1;
		
		int tokenId = tokenService.getTokenId(token);
		TokenUsage usage = new TokenUsage();
		usage.setTokenId(tokenId);
		if(url.contains("?")){
			url = url.split("?")[0];
		}
		usage.setUri(url);
		usage.setDate(new Date());
		return this.insert(usage);
		
	}

	@Override
	public int tokenCount(int tokenId) {
		
		return tokenUsageDao.tokenCount(tokenId);
	}

}
