package com.park.dao;

import org.springframework.stereotype.Repository;

import com.park.model.Token;

@Repository
public interface TokenDAO {
	
	public int getTokenCount(String token);
	
	public int getTokenId(String token);
	
	public int insert(Token token);
	
	public String getToken(int id);

}
