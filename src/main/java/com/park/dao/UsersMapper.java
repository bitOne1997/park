package com.park.dao;

import java.util.List;



import org.apache.ibatis.annotations.Param;

import com.park.model.Users;

public interface UsersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);    

    int getCount();
    
    List<Users> getByCount(@Param("start")Integer start,@Param("count")Integer count);
    
    List<Users> getByUserName(@Param("userName")String userName);
    
    List<Users> getByUserNameAndPassword(@Param("userName")String userName,@Param("password")String password);
}