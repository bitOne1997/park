package com.park.service;

import java.util.List;

import com.park.model.TokenUsage;
import com.park.model.UserTokenUsage;

public interface TokenUsageService {
	
	public int count();
	
	public int tokenCount(int tokenId);
	
	public List<UserTokenUsage> get(int begin, int len);
	
	public List<UserTokenUsage> getUsage(int userId);
	
	public int insert(TokenUsage usage);
	
	public int insertRecord(String token, String url);

}
