package com.rdcentermrzhi.java.demo.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeZoneTest {
	
	public static void main(String[] args) throws ParseException
	{
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date  date = sdf2.parse("2011-01-01 00:00:00");
		
		
		
		TimeZone.setDefault(TimeZone.getTimeZone("Australia/Sydney"));
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss  Z z");
		
		//Date  date = sdf2.parse("2011-01-01 00:00:00");
		
		String astr = 	sdf1.format(date);
		System.out.println(astr);
	}

}
