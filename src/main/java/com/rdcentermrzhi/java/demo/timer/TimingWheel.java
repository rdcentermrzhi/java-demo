package com.rdcentermrzhi.java.demo.timer;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TimingWheel {

	public TimingWheel(Long tickMs, Integer wheelSize, Long startMs, AtomicInteger taskCounter,
			DelayQueue<TimerTaskList> queue) {
		super();
		this.tickMs = tickMs;
		this.wheelSize = wheelSize;
		this.startMs = startMs;
		this.taskCounter = taskCounter;
		this.queue = queue;

		buckets = new TimerTaskList[wheelSize];
		for (int i = 0; i < wheelSize; i++) {
			buckets[i] = new TimerTaskList(taskCounter);
		}

		interval = tickMs * wheelSize;
		currentTime = startMs - (startMs % tickMs);
	}

	private Long tickMs;
	private Integer wheelSize;
	private Long startMs;
	private AtomicInteger taskCounter;
	private DelayQueue<TimerTaskList> queue;

	private Long interval;
	private TimerTaskList[] buckets;

	private Long currentTime;

	private TimingWheel overflowWheel = null;

	private void addOverflowWheel() {
		synchronized (this) {
			if (overflowWheel == null) {
				overflowWheel = new TimingWheel(interval, wheelSize, currentTime, taskCounter, queue);
			}
		}

	}

	boolean add(TimerTaskEntry timerTaskEntry) {
		long expiration = timerTaskEntry.getExpirationMs();

		if (timerTaskEntry.cancelled()) {
			// Cancelled
			return false;
		} else if (expiration < currentTime + tickMs) {
			// Already expired
			return false;
		} else if (expiration < currentTime + interval) {
			// Put in its own bucket
			long virtualId = expiration / tickMs;
			TimerTaskList bucket = buckets[(int) (virtualId % new Long(wheelSize))];
			bucket.add(timerTaskEntry);

			// Set the bucket expiration time
			if (bucket.setExpiration(virtualId * tickMs)) {
				// The bucket needs to be enqueued because it was an expired bucket
				// We only need to enqueue the bucket when its expiration time has changed, i.e.
				// the wheel has advanced
				// and the previous buckets gets reused; further calls to set the expiration
				// within the same wheel cycle
				// will pass in the same value and hence return false, thus the bucket with the
				// same expiration will not
				// be enqueued multiple times.
				queue.offer(bucket);
			}
			return true;
		} else {
			// Out of the interval. Put it into the parent timer
			if (overflowWheel == null)
				addOverflowWheel();
			return overflowWheel.add(timerTaskEntry);
		}
	}

	void advanceClock(Long timeMs) {
		if (timeMs >= currentTime + tickMs) {
			currentTime = timeMs - (timeMs % tickMs);

			// Try to advance the clock of the overflow wheel if present
			if (overflowWheel != null)
				overflowWheel.advanceClock(currentTime);
		}
	}

}
