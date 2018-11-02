package com.rdcentermrzhi.java.demo.timer;

import java.util.PriorityQueue;

public class MockTimer implements Timer{
	private Timer time = new MockTimer();
	private PriorityQueue<TimerTaskEntry> taskQueue = new PriorityQueue<>();
	
	@Override
	public void add(TimerTask timerTask) {
		if(timerTask.delayMs<= 0) {
			timerTask.run();
		}else {
			/*taskQueue.add(new TimerTaskEntry(timerTask, timerTask.delayMs + time.milliseconds))*/
		}
		
		
		
	}
	
	
	
}
