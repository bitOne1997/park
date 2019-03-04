package com.park.service;

public interface TokenService {
	
	public boolean validToken(String token);
	
	public int getTokenId(String token);
	
	public int insertToken();
	
	public String getToken(int id);

}
