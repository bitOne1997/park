package com.park.service.impl;
import java.util.Map;

import org.springframework.stereotype.Service;

import java.util.HashMap; 
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Calendar;
@Service
public class FormatTime
{ 
    //startTime and endTime should be in format like: yyyy-MM-dd HH:mm:ss
	//start and end should be in format arrange from 0 to 23
	public Map<String,String> format(String startTime,String endTime,String start, String end)
	{
	    Map<String, String> ret_map = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
		    Date Startdate = sdf.parse(startTime);
		    Date Enddate = sdf.parse(endTime);
		
		    if((Startdate == null) || (Enddate == null) || (start == null) || (end == null))
		    {
			    System.out.format("param error");
		        return null;
		    }
			int intstart = Integer.parseInt(start);
			int intend = Integer.parseInt(end);
			if(intstart == intend)
			{
			    ret_map.put(startTime, endTime);
				return ret_map;
			}
			
			if(Startdate.compareTo(Enddate) > 0)
			{
			    System.out.format("starttime or endtime error");
			    return null;
			}
			
			Calendar Cstart = Calendar.getInstance();
			Calendar Cend = Calendar.getInstance();
			Cstart.setTime(Startdate);
			Cend.setTime(Enddate);
			
			long lstart = Cstart.getTimeInMillis();
			long lend = Cend.getTimeInMillis();
			
			int start_hour = Cstart.get(Calendar.HOUR_OF_DAY);
			int start_minute = Cstart.get(Calendar.MINUTE);    
            int start_second = Cstart.get(Calendar.SECOND);
			
			long ltmp = lstart + 60*60*1000 - start_minute*60*1000 - start_second*1000;  //The next hour to check.
			long key = lstart;
			Calendar Ctmp = Calendar.getInstance(); 
			if(ltmp >= lend)
			{
			    ret_map.put(startTime, endTime);
				return ret_map;
			}
			else
			{
			    SimpleDateFormat Stmp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    do
				{
                    Ctmp.setTimeInMillis(ltmp);  
                
				    int hour_tmp = Ctmp.get(Calendar.HOUR_OF_DAY);
				    if((hour_tmp == intstart) || (hour_tmp == intend) )
				    {  
                        String Svalue = Stmp.format(Ctmp.getTime()); 
                    
					    Ctmp.setTimeInMillis(key);
                        String Skey = Stmp.format(Ctmp.getTime()); 
                        ret_map.put(Skey, Svalue);
                        key = ltmp;	
                    }
		
			        ltmp += 60*60*1000;
			   }while(ltmp < lend);
			   
			   Ctmp.setTimeInMillis(key);
               String Skey = Stmp.format(Ctmp.getTime()); 
               ret_map.put(Skey, endTime);
			}
		
		} catch (ParseException e) {
            e.printStackTrace();
			return null;
        }
		
	    return ret_map;
	}
	
	public static void main (String[] args) 
   { 
        FormatTime f = new FormatTime();
        System.out.println("in....");
        Map<String, String> env = f.format("2016-12-10 09:20:36","2016-12-15 12:59:36","24","0"); 
		if(env == null)
		{
		    System.out.format("format error");
		}
		else
		{
            for (String envName : env.keySet()) 
            { 
                System.out.format("%s  %s%n", envName, env.get(envName)); 
            }
        }		
    }
} 