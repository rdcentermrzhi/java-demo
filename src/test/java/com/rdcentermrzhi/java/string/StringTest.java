package com.rdcentermrzhi.java.string;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringTest {
	private static final Logger logger = LoggerFactory.getLogger(StringTest.class);
	
	public static void main(String[] args) {
		
		
		int a =  -3 ;
		int b = 2;
		System.out.println(a/b);
		String x = String.format("{}", "aaa");
		
		
		logger.info("{}","aaa");
		
		
	}
}
