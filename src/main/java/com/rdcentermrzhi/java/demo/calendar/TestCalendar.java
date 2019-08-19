package com.rdcentermrzhi.java.demo.calendar;

import java.util.Calendar;
import java.util.TimeZone;

public class TestCalendar {

	public static void main(String[] args)
	{
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Los_Angeles"));
		
		cal.set(2019, 4, 20, 0, 0, 0);
		
		System.out.println(cal.getTime().getTime());
		
		
		
		
		
		
	}
	
	
}
