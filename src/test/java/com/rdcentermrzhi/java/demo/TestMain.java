package com.rdcentermrzhi.java.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
		
		/*DelayQueue<TestDelay> delayQueue =  new DelayQueue<>();
		
		//delayQueue
		
		for(int i = 0 ; i < 100; i++) {
			long t1  = System.currentTimeMillis();
			delayQueue.poll(1, TimeUnit.SECONDS);
			System.out.println(System.currentTimeMillis() - t1);
		}*/
		
		ExecutorService service =  Executors.newFixedThreadPool(10);
		CompletableFuture<Void> future = CompletableFuture.runAsync(()->{
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("开始执行");
		}, service);
		
		future.thenRun(()->{
			System.out.println("执行完成");
		});
		
		
		System.out.println("任务添加完成");
		
		/*	CompletableFuture<String> future =  service.submit(()->{
			
			return "";
		});*/
		
		
		
	}
}

