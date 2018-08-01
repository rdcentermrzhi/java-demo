package com.rdcentermrzhi.java.demo;

public class TestMain {
	
	
	public static void main(String[] args) {
		
		for(int i = 0 ;i < 10; i++) {
			new Thread(()->{
				System.out.println("" + Thread.currentThread().getName());
			}).start();  
		}
	}
}

