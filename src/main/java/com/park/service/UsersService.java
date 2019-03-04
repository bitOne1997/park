package com.park.service;

import java.util.List;


import com.park.model.Users;

public interface UsersService {
    int deleteByPrimaryKey(Integer id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);
    
    int getCount();
    
    List<Users> getByCount(Integer start,Integer count);
    
    List<Users> getByUserName(String userName);
    
    List<Users> getByUserNameAndPassword(String userName,String password);
}
