package com.rdcentermrzhi.java.demo.hystrix;

import java.util.concurrent.TimeUnit;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;


//使用Fallback() 提供降级策略
public class FallBackHelloWorld  extends HystrixCommand<String>{

	private String name;
	protected FallBackHelloWorld(String name) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("hello-World"))
				.andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(500)));
		this.name = name;
	}
	

	@Override
	protected String run() throws Exception {
		TimeUnit.MILLISECONDS.sleep(400);
		return "hello " + name + " " + Thread.currentThread().getName();
	}

	
	
	@Override
	protected String getFallback() {
		//super.getFallback();
		return "fall back execute()";
	}


	public static void main(String[] args) {
		FallBackHelloWorld cmd = new FallBackHelloWorld("slots");
		String res = cmd.execute();
		
		System.out.println(res);
		
	}
}
