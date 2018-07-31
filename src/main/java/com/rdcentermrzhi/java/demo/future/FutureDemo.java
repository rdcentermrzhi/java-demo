package com.rdcentermrzhi.java.demo.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/*
 * 
 * java Future
 * 
 */
public class FutureDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Future 接口实现
		//interfaceFuture();
		// FutureTask 实现
		impleFutureTask();
	}

	public static void interfaceFuture() {
		ExecutorService service = Executors.newCachedThreadPool();
		Future<Integer> future = service.submit(new CallBackTask());
		try {
			service.shutdown();
			Thread.sleep(2000);

			// get 会造成线程阻塞
			System.out.println(future.get() + "\t" + System.currentTimeMillis());

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void impleFutureTask() {
		ExecutorService service = Executors.newCachedThreadPool();
		FutureTask<Integer> task = new FutureTask<Integer>(new CallBackTask());
		service.submit(task);

		service.shutdown();

		try {
			Thread.sleep(2000);
			// get 会造成线程阻塞
			System.out.println(task.get() + "\t" + System.currentTimeMillis());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

class CallBackTask implements Callable<Integer> {

	public Integer call() throws Exception {

		try {
			System.out.println(System.currentTimeMillis());
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return -1;
		}

		return 0;
	}

}
