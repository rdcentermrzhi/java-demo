package com.rdcentermrzhi.java.demo.timewheel;

import java.util.Iterator;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rdcentermrzhi.java.demo.date.TimeUtil;

@ThreadSafe
class TimerTaskList implements Delayed, Iterable<TimerTask> {
	private static final Logger logger =  LoggerFactory.getLogger(TimerTaskList.class);

	public TimerTaskList(AtomicInteger taskCounter) {
		this.taskCounter = taskCounter;
		root.next = root;
		root.prev = root;
	}

	// TimerTaskList forms a doubly linked cyclic list using a dummy root entry
	// root.next points to the head
	// root.prev points to the tail
	private TimerTaskEntry root = new TimerTaskEntry(null, -1);

	public AtomicInteger taskCounter;

	private AtomicLong expiration = new AtomicLong(-1L);

	public boolean setExpiration(Long expirationMs) {
		return this.expiration.getAndSet(expirationMs) != expirationMs;
	}

	public Long getExpiration() {
		return expiration.get();
	}

	@Override
	public void forEach(Consumer<? super TimerTask> f) {
		synchronized (this) {
			TimerTaskEntry entry = root.next;
			while (!entry.equals(root)) {
				TimerTaskEntry nextEntry = entry.next;

				if (!entry.cancelled())
					f.accept(entry.timerTask);

				entry = nextEntry;
			}
		}
	}

	public void add(TimerTaskEntry timerTaskEntry) {
		boolean done = false;
		while (!done) {
			// Remove the timer task entry if it is already in any other list
			// We do this outside of the sync block below to avoid deadlocking.
			// We may retry until timerTaskEntry.list becomes null.
			timerTaskEntry.remove();

			synchronized (this) {
				synchronized (timerTaskEntry) {
					if (timerTaskEntry.list == null) {
						// put the timer task entry to the end of the list. (root.prev points to the
						// tail entry)
						TimerTaskEntry tail = root.prev;
						timerTaskEntry.next = root;
						timerTaskEntry.prev = tail;
						timerTaskEntry.list = this;
						tail.next = timerTaskEntry;
						root.prev = timerTaskEntry;
						taskCounter.incrementAndGet();
						done = true;
					}
				}
			}
		}
	}

	// Remove the specified timer task entry from this list
	public void remove(TimerTaskEntry timerTaskEntry) {
		synchronized (this) {
			synchronized (timerTaskEntry) {
				if (timerTaskEntry.list.equals(this)) {
					timerTaskEntry.next.prev = timerTaskEntry.prev;
					timerTaskEntry.prev.next = timerTaskEntry.next;
					timerTaskEntry.next = null;
					timerTaskEntry.prev = null;
					timerTaskEntry.list = null;
					taskCounter.decrementAndGet();
				}
			}
		}

	}

	// Remove all task entries and apply the supplied function to each of them
	public void flush(Consumer<TimerTaskEntry> f) {
		synchronized (this) {
			TimerTaskEntry head = root.next;
			while (head != root) {
				remove(head);
				//
				// FunctionInterface
				// timer.addTimerTaskEntry(head);
				f.accept(head);
				head = root.next;
			}
			expiration.set(-1L);
		}
	}

	@Override
	public int compareTo(Delayed d) {

		TimerTaskList other = (TimerTaskList) d;
		if (getExpiration() < other.getExpiration())
			return -1;
		else if (getExpiration() > other.getExpiration())
			return 1;
		else
			return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(Math.max(getExpiration() - System.currentTimeMillis(), 0), TimeUnit.MILLISECONDS);
	}

	@Override
	public Iterator<TimerTask> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}

class TimerTaskEntry implements Comparable<TimerTaskEntry> {
	public volatile TimerTaskList list;
	public TimerTaskEntry next;
	public TimerTaskEntry prev;
	public TimerTask timerTask;
	public Long expirationMs;

	public TimerTaskEntry(TimerTask timerTask, long expirationMs) {
		this.timerTask = timerTask;
		this.expirationMs = expirationMs;
		if (timerTask != null)
			timerTask.setTimerTaskEntry(this);
	}

	public boolean cancelled() {
		return timerTask.getTimerTaskEntry() != this;
	}

	public void remove() {
		TimerTaskList currentList = list;

		// If remove is called when another thread is moving the entry from a task entry
		// list to another,
		// this may fail to remove the entry due to the change of value of list. Thus,
		// we retry until the list becomes null.
		// In a rare case, this thread sees null and exits the loop, but the other
		// thread insert the entry to another list later.
		while (currentList != null) {
			currentList.remove(this);
			currentList = list;
		}

	}

	@Override
	public int compareTo(TimerTaskEntry o) {
		// System.out.println(o.expirationMs);
		return this.expirationMs.compareTo(o.expirationMs);
	}

	@Override
	public String toString() {
		return "TimerTaskEntry [timerTask=" + timerTask + ", expirationMs=" + expirationMs + "]";
	}

	
}