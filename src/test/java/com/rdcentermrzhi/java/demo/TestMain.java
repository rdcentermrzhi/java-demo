package com.rdcentermrzhi.java.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class TestMain {
	
	
	public static void main(String[] args) throws InterruptedException {
		
/*		for(int i = 0 ;i < 100; i++) {
			new Thread(()->{
				System.out.println("" + Thread.currentThread().getName());
			}).start();  
			System.out.print(System.currentTimeMillis());
			System.out.println("\t" +new TestClass().getCurr());
			
		}
		
		List<String> list = new ArrayList<>();*/
		//list.forEach(action);
	/*  P p = new SonP();
		
		System.out.println(p.getXx());*/
		
		DelayQueue<TestDelay> delayQueue =  new DelayQueue<>();
		
		//delayQueue
		
		for(int i = 0 ; i < 100; i++) {
			long t1  = System.currentTimeMillis();
			delayQueue.poll(1, TimeUnit.SECONDS);
			System.out.println(System.currentTimeMillis() - t1);
		}
		
		
	}
}

