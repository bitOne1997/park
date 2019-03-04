package com.park.service;

import java.util.List;


import com.park.model.Authorityadmin;

public interface AuthorityadminService {
	int deleteByPrimaryKey(Integer id);

    int insert(Authorityadmin record);

    int insertSelective(Authorityadmin record);

    Authorityadmin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Authorityadmin record);

    int updateByPrimaryKey(Authorityadmin record);
    
    List<Authorityadmin> getByToken(String token);
    
    List<Authorityadmin> getAll();
}
