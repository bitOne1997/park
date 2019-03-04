package com.park.service;

import com.park.model.ChargedataPark;
import com.park.model.ChargedataParkWithTable;

public interface ChargeDataService {

	 	int deleteByPrimaryKey(Integer id);

	    int insert(ChargedataPark record);

	    int insertSelective(ChargedataPark record);

	    ChargedataPark selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(ChargedataPark record);

	    int updateByPrimaryKey(ChargedataPark record);
	    
	    int insertTable(ChargedataParkWithTable record);
	    
	    int insertSelectiveTable(ChargedataParkWithTable record);
	    
	    int updateByPrimaryKeyTable(ChargedataParkWithTable record);
	    
	    void generateTable(int parkId);
}
