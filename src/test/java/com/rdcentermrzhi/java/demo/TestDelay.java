package com.rdcentermrzhi.java.demo;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class TestDelay  implements Delayed{
	
	private long delayMs;

	@Override
	public int compareTo(Delayed o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(Math.max(delayMs - System.currentTimeMillis(), 0), TimeUnit.MILLISECONDS);
	}

}
