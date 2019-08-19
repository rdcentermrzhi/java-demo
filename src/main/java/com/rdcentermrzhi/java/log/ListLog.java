package com.rdcentermrzhi.java.log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class ListLog {

	private static List<String> greenList = new ArrayList<String>(1000);

	private static List<String> blueList = new ArrayList<String>(1000);

	private static AtomicBoolean write = new AtomicBoolean(false);
	
	
	private static ConcurrentLinkedQueue<String> cuList =  new ConcurrentLinkedQueue<String>();
	
	
	private  static  CountDownLatch countDownLatch =  new CountDownLatch(10);

	public static List<String> getWriteList() {

		if (write.get())
			return blueList;
		return greenList;
	}

	public static void main(String[] args) throws InterruptedException {
		
		
		long t =  System.currentTimeMillis();
		
		new MyWriter().start();

		for (int i = 0; i < 10; i++) {
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					for (int j = 0; j < 1000; j++) {
						// getWriteList().add(Thread.currentThread().getName() + ": " + j);
						cuList.add(Thread.currentThread().getName() + ": " + j);
					}
					
					countDownLatch.countDown();
				}
			}).start();
			
			
		}
		
		
		countDownLatch.await();
		System.err.println("结束：" + (System.currentTimeMillis() - t));
		
		
		
		
	}

	static class MyWriter extends Thread {

		@Override
		public void run() {

			while (true) {
				
				List<String> list =  new ArrayList<String>();
				String tmp = cuList.poll();
				while(tmp!= null){
					list.add(tmp);
					tmp = cuList.poll();
				}
				
				
				for(String a : list) {
					int i =1;
					i++ ;
				}

				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			/*	List<String> writeList = null;
				try {
					if (write.get()) {
						writeList = greenList;
					} else {
						writeList = blueList;
					}
					//
					try {
						for (String key : writeList) {
							int i =1;
								i++ ;
							//System.err.println(key);
						}
					} finally {
						writeList.clear();
					}

					//单线程操作
					if (write.get()) {
						write.set(false);
					} else {
						write.set(true);;
					}
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}*/
			}

		}

	}

}
