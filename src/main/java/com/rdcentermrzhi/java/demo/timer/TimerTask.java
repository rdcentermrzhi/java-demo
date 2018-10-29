package com.rdcentermrzhi.java.demo.timer;

public abstract class TimerTask implements Runnable {

	public long delayMs; // timestamp in millisecond

	private TimerTaskEntry timerTaskEntry;

	public void cancel() {
		synchronized (this) {
			if (timerTaskEntry != null)
				timerTaskEntry.remove();
			timerTaskEntry = null;
		}
	}

	public void setTimerTaskEntry(TimerTaskEntry entry) {
		synchronized (this) {
			// if this timerTask is already held by an existing timer task entry,
			// we will remove such an entry first.
			if (timerTaskEntry != null && timerTaskEntry != entry)
				timerTaskEntry.remove();

			timerTaskEntry = entry;
		}
	}

	public TimerTaskEntry getTimerTaskEntry() {
		return timerTaskEntry;
	}

}
