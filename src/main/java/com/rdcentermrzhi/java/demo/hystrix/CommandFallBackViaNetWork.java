package com.rdcentermrzhi.java.demo.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

public class CommandFallBackViaNetWork  extends HystrixCommand<String>{
	private final int id;

	protected CommandFallBackViaNetWork(int id) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteServiceX"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("GetValueCommand")));
		this.id = id;
	}

	@Override
	protected String run() throws Exception {
		throw new RuntimeException("force failure for example"); 
	}

	@Override
	protected String getFallback() {
		
		return new FallBackViaNetWork(id).execute();  
	}
	

	
	private static class FallBackViaNetWork extends HystrixCommand<String> {
		private final int id;

		protected FallBackViaNetWork(int id) {
			super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteServiceX"))
					.andCommandKey(HystrixCommandKey.Factory.asKey("GetFallBackValueCommand"))
					// 使用不同的线程池做隔离，防止上层线程池跑满，影响降级逻辑.
					.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("RemoteServiceXFallback"))
					);
			
			this.id = id;
		}

		@Override
		protected String run() throws Exception {
			//MemCacheClient.getValue(id);  
			return null;
		}
		
		@Override  
        protected String getFallback() {  
            return null;  
        } 
		
	}
	

}
