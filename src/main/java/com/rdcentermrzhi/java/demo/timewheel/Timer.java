package com.rdcentermrzhi.java.demo.timewheel;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rdcentermrzhi.java.demo.date.TimeUtil;

public interface Timer {
	/**
	 * Add a new task to this executor. It will be executed after the task's delay
	 * (beginning from the time of submission)
	 * 
	 * @param timerTask
	 *            the task to add
	 */
	default void add(TimerTask timerTask) {
	}

	/**
	 * Advance the internal clock, executing any tasks whose expiration has been
	 * reached within the duration of the passed timeout.
	 * 
	 * @param timeoutMs
	 * @return whether or not any tasks were executed
	 */
	default boolean advanceClock(Long timeoutMs) {
		return false;
	}

	/**
	 * Get the number of tasks pending execution
	 * 
	 * @return the number of tasks
	 */
	default int size() {
		return 0;
	}

	/**
	 * Shutdown the timer service, leaving pending tasks unexecuted
	 */
	default void shutdown() {
	}
}

class SystemTimer implements Timer {
	private static final Logger logger = LoggerFactory.getLogger(SystemTimer.class);

	public String executorName_;
	public Long tickMs_ = 1L;
	public int wheelSize_ = 20;
	public Long startMs_ = System.currentTimeMillis();

	private ExecutorService taskExecutor;

	private DelayQueue<TimerTaskList> delayQueue = new DelayQueue<>();
	private AtomicInteger taskCounter = new AtomicInteger(0);
	private TimingWheel timingWheel;

	private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
	private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

	public SystemTimer(String executorName) {

		this.executorName_ = executorName;
		taskExecutor = Executors.newFixedThreadPool(1, new ThreadFactory() {

			@Override
			public Thread newThread(Runnable runnable) {
				return Utils.newThread("executor-" + executorName_, runnable, false);
			}
		});
		timingWheel = new TimingWheel(tickMs_, wheelSize_, startMs_, taskCounter, delayQueue);
	}

	public SystemTimer(String executorName, Long tickMs, int wheelSize) {

		this.executorName_ = executorName;
		taskExecutor = Executors.newFixedThreadPool(1, new ThreadFactory() {

			@Override
			public Thread newThread(Runnable runnable) {
				return Utils.newThread("executor-" + executorName_, runnable, false);
			}
		});
		wheelSize_ = wheelSize;
		tickMs_ = tickMs;
		timingWheel = new TimingWheel(tickMs_, wheelSize_, startMs_, taskCounter, delayQueue);
	}

	@Override
	public void add(TimerTask timerTask) {
		readLock.lock();
		try {

			addTimerTaskEntry(new TimerTaskEntry(timerTask, System.currentTimeMillis() + timerTask.delayMs));

		} finally {
			readLock.unlock();
		}

	}

	private void addTimerTaskEntry(TimerTaskEntry timerTaskEntry) {
		if (!timingWheel.add(timerTaskEntry)) {
			// Already expired or cancelled
			if (!timerTaskEntry.cancelled())
				taskExecutor.submit(timerTaskEntry.timerTask);
		}
	}

	private Consumer<TimerTaskEntry> reinsert = new Consumer<TimerTaskEntry>() {
		@Override
		public void accept(TimerTaskEntry t) {
			addTimerTaskEntry(t);

		}
	};

	/*
	 * Advances the clock if there is an expired bucket. If there isn't any expired
	 * bucket when called, waits up to timeoutMs before giving up.
	 */
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
					bucket.flush(reinsert);
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