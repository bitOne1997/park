package com.park.model;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class SingleParkInfoItem {
@XStreamImplicit(itemFieldName = "Park")	
 SingleParkInfo singleParkInfos=null;

public SingleParkInfo getSingleParkInfos() {
	return singleParkInfos;
}

public void setSingleParkInfos(SingleParkInfo singleParkInfos) {
	this.singleParkInfos = singleParkInfos;
}



}
