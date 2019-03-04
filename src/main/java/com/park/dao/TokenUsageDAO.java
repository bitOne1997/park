package com.park.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.park.model.TokenUsage;
import com.park.model.UserTokenUsage;

@Repository
public interface TokenUsageDAO {
	
	public int count();
	
	public int tokenCount(int tokenId);
	
	public List<UserTokenUsage> get(int begin, int len);
	
	public List<UserTokenUsage> getUsage(int userId);
	
	public int insertUsage(TokenUsage usage);

}
