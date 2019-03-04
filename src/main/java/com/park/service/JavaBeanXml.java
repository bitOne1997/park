package com.park.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;

import org.apache.xmlbeans.impl.values.XmlAnySimpleTypeImpl;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.park.model.Park;
import com.park.model.SingleParkInfo;
import com.park.model.SingleParkInfoItem;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
@Service
public class JavaBeanXml {
	
	@Autowired
	private ParkService parkService;

	public Object SingleParkInfoFromXml(String xml) throws DocumentException{
	//	InputStream xmlInputStream = new ClassPathResource(xml).getInputStream();
		XStream xStream = new XStream(new DomDriver());		
		xStream.alias("Item", SingleParkInfoItem.class);
		xStream.aliasField("Park", SingleParkInfoItem.class,"singleParkInfos");
		xStream.aliasField("ParkId", SingleParkInfo.class,"ParkId");
		xStream.aliasField("TotRemainNum", SingleParkInfo.class,"TotRemainNum");
		xStream.aliasField("MonthlyRemainNum", SingleParkInfo.class,"MonthlyRemainNum");
		xStream.aliasField("GuesRemainNum", SingleParkInfo.class,"GuesRemainNum");
		xStream.aliasField("Updatetime", SingleParkInfo.class,"Updatetime");
		xStream.aliasField("Sendtime", SingleParkInfo.class,"Sendtime");
		SingleParkInfoItem singleParkInfos= (SingleParkInfoItem) xStream.fromXML(xml);		
		return  singleParkInfos.getSingleParkInfos();
	}
	  
	public void updateParkFromXml() throws DocumentException{
		ParkServletService ParkWS = new ParkServletService();
		ParkServletDelegate ParkDelegate = ParkWS.getParkServletPort();
		List<Map<Integer, String>> parkid=new ArrayList<>();
	//	parkid.add({"pt31010700215","198"});
		Map<Integer, String> aa= new HashMap<>();
		aa.put(198, "pt31010700049");
		parkid.add(aa);
		Map<Integer, String> ab= new HashMap<>();
		ab.put(199, "pt31010700228");
		parkid.add(ab);
		Map<Integer, String> ac= new HashMap<>();
		ac.put(200, "pt31010700079");
	//	parkid.add(ac);
		Map<Integer, String> ad= new HashMap<>();
		ad.put(201, "pt31010700215");
	//	parkid.add(ad);
		Map<Integer, String> ae= new HashMap<>();
		ae.put(203, "pt31010700040");
		parkid.add(ae);

		for (Map<Integer, String> parkmap : parkid) {
			 Set<Integer> key = parkmap.keySet();
		        for (Iterator it = key.iterator(); it.hasNext();) {
		        	Integer Id = (Integer) it.next();
		        	String xml=ParkDelegate.singleParkInfo("admin_pt", "cadre_pt", parkmap.get(Id));
		   //         System.out.println(xml);
		        	if (xml==null||xml.equals("")) {
						continue;
					}
		            SingleParkInfo singleParkInfo=(SingleParkInfo) SingleParkInfoFromXml(xml);
		            String tmp=singleParkInfo.getUpdatetime();
					String updateTime=tmp.substring(0, 4)+"-"+tmp.substring(4,6)+"-"+tmp.substring(6,8)+" "+
					tmp.substring(8,10)+":"+tmp.substring(10, 12)+":"+tmp.substring(12,14);
					Park parkselect=parkService.getParkById(Id);
					parkselect.setPortLeftCount(Integer.parseInt(singleParkInfo.getTotRemainNum()));
					parkselect.setDate(updateTime);
					parkService.updatePark(parkselect);
			//		 System.out.println(updateTime);
		        }
		
		}
	
	//	Park parkselect =parkService.getParkById(198);
	}
	 
}
