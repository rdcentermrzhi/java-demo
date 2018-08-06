package com.rdcentermrzhi.java.demo.hystrix;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import rx.Observable;
import rx.Observer;

public class CommandHelloWorld extends HystrixCommand<String> {
	private final String name;

	protected CommandHelloWorld(String name) {
		// 最少配置:指定命令组名(CommandGroup)
		super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
		this.name = name;
	}

	@Override
	protected String run() throws Exception {
		Thread.sleep(1L);
		return "Hello " + name + "\tThread:" + Thread.currentThread().getName();
	}

	public static void main(String[] args) {

	
		CommandHelloWorld helloCommand = new CommandHelloWorld("Synchronous-hystrix");
		 //使用execute()同步调用代码,效果等同于:helloWorldCommand.queue().get();
		String res = helloCommand.execute();
		System.out.println("result=" + res);
		//每个Command对象只能调用一次,不可以重复调用,  
       //重复调用对应异常信息:This instance can only be executed once. Please instantiate a new instance
		/*String res1 = helloCommand.execute();
		System.out.println("result=" + res1);*/
		
		
		
		helloCommand = new CommandHelloWorld("Asynchronous-hystrix");
		//异步调用等待结果
		Future<String> future = helloCommand.queue();
		
		try {
			res = future.get(100, TimeUnit.DAYS);
			System.out.println("result="+ res);
			System.out.println("Main Thread= " + Thread.currentThread().getName());
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//注册异步事件回调执行
		//注册观察者事件拦截
		Observable<String> fs = new CommandHelloWorld("World").observe();
		//注册结果回调
		fs.subscribe((String result) -> {
			System.out.println(System.currentTimeMillis() + result);
		});
		
		
		//注册完整执行生命周期事件  
		fs.subscribe(new Observer<String>() {  
		            @Override  
		            public void onCompleted() {  
		                // onNext/onError完成之后最后回调  
		                System.out.println("execute onCompleted");  
		            }  
		            @Override  
		            public void onError(Throwable e) {  
		                // 当产生异常时回调  
		                System.out.println("onError " + e.getMessage());  
		                e.printStackTrace();  
		            }  
		            @Override  
		            public void onNext(String v) {  
		                // 获取结果后回调  
		                System.out.println("onNext: " + v);  
		            }  
		        });  
		

	}
}
