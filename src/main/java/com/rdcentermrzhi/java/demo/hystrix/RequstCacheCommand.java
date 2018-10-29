package com.rdcentermrzhi.java.demo.hystrix;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import junit.framework.Assert;


public class RequstCacheCommand extends HystrixCommand<String> {
	private final int id ;

	protected RequstCacheCommand(int id) {
		super(HystrixCommandGroupKey.Factory.asKey("requestCacheCommand"));
		this.id = id;
	}

	@Override
	protected String run() throws Exception {
		System.out.println(Thread.currentThread().getName() + " execute id = " + id);
		return "execute id = " + id;
	}

	@Override
	protected String getCacheKey() {
		
		return String.valueOf(id);
	}
	
	
	public static void main(String[] args) {
		HystrixRequestContext context =  HystrixRequestContext.initializeContext();
		try {
			RequstCacheCommand cmd2a = new RequstCacheCommand(2);
			RequstCacheCommand cmd2b = new RequstCacheCommand(2);
			//isResponseFromCache判定是否是在缓存中获取结果  
            Assert.assertFalse(cmd2a.isResponseFromCache());  
           // Assert.assertTrue(cmd2b.execute());  
            Assert.assertTrue(cmd2b.isResponseFromCache());  
		}finally {
			context.shutdown();
		}
	}

	
}
