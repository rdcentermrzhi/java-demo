package com.rdcentermrzhi.java.demo.timer;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

interface Timer {
	void add(TimerTask timerTask);

	boolean advanceClock(Long timeoutMs);

	int size();

	void shutdown();
}

class SystemTimer implements Timer {

	private String executorName;
	private Long tickMs = 1L;
	private int wheelSize = 20;
	private Long startMs = System.currentTimeMillis();

	private ExecutorService taskExecutor;

	private DelayQueue<TimerTaskList> delayQueue;
	private AtomicInteger taskCounter = new AtomicInteger(0);
	private TimingWheel timingWheel;

	private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private ReentrantReadWriteLock.ReadLock readLock;
	private ReentrantReadWriteLock.WriteLock writeLock;

	public SystemTimer(String executorName) {
		this.executorName = executorName;
		taskExecutor = Executors.newFixedThreadPool(1, new ThreadFactory() {

			@Override
			public Thread newThread(Runnable runnable) {
				return Utils.newThread("executor-" + executorName, runnable, false);
			}
		});
		readLock = readWriteLock.readLock();
		writeLock = readWriteLock.writeLock();
		timingWheel = new TimingWheel(tickMs, wheelSize, startMs, taskCounter, delayQueue);
	}

	@Override
	public void add(TimerTask timerTask) {
		readLock.lock();
		try {
			addTimerTaskEntry(new TimerTaskEntry(timerTask, timerTask.delayMs + System.currentTimeMillis()));
		} finally {
			readLock.unlock();
		}

	}

	public void addTimerTaskEntry(TimerTaskEntry timerTaskEntry) {
		if (!timingWheel.add(timerTaskEntry)) {
			// Already expired or cancelled
			if (!timerTaskEntry.cancelled())
				taskExecutor.submit(timerTaskEntry.getTimerTask());
		}
	}

	@Override
	public boolean advanceClock(Long timeoutMs) {
		TimerTaskList bucket = null;
		try {
			bucket = delayQueue.poll(timeoutMs, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (bucket != null) {
			writeLock.lock();
			try {
				while (bucket != null) {
					timingWheel.advanceClock(bucket.getExpiration());
					bucket.flush(this);
					bucket = delayQueue.poll();
				}
			} finally {
				writeLock.unlock();
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int size() {
		return taskCounter.get();
	}

	@Override
	public void shutdown() {
		taskExecutor.shutdown();
	}

}