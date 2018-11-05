package com.rdcentermrzhi.java.demo.date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	
	
	public static String getDateString(String pattern, long time) {
		return new SimpleDateFormat(pattern).format(new Date(time));
	}
	
}
