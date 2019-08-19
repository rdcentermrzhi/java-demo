package com.rdcentermrzhi.java.string;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringTest {
	private static final Logger logger = LoggerFactory.getLogger(StringTest.class);
	
	public static void main(String[] args) {
		
		
		int a =  -3 ;
		int b = 2;
		System.out.println(a/b);
		String x = String.format("%s ", "aaa");
		
		System.out.println(x);
		
		//logger.info("{}","aaa");
		
		//java.util.Formatter.format(String,Object...);
		
		//char  b = '你';
		
		
		
		for(int i = 0 ; i < 1000; i++) {
			
			System.out.println("main" +i);
			
		}
		
		
		new Thread(()->{
			for(int i = 0 ; i < 1000; i++) {
				
				System.out.println("thread" +i);
				
			}
			
			
		}) .start();
		
		
		
		
		
		
	}
}
