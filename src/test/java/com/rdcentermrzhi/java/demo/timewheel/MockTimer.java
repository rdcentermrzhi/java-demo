package com.rdcentermrzhi.java.demo.timewheel;

import java.util.PriorityQueue;

import com.rdcentermrzhi.java.demo.timewheel.Timer;
import com.rdcentermrzhi.java.demo.timewheel.TimerTask;

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
