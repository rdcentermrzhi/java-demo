package com.rdcentermrzhi.java.demo.timewheel.netty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import io.netty.util.HashedWheelTimer;

public class HashedWheelTimerTest {
	
	
	@Test
	public void test1() throws Exception {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    HashedWheelTimer hashedWheelTimer = new HashedWheelTimer(100, TimeUnit.MILLISECONDS);
	    System.out.println("start:" + LocalDateTime.now().format(formatter));
	    hashedWheelTimer.newTimeout(timeout -> {
	        System.out.println("task :" + LocalDateTime.now().format(formatter));
	    }, 3, TimeUnit.SECONDS);
	    Thread.sleep(5000);
	}

}
