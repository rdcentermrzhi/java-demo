package com.rdcentermrzhi.java.demo.timer;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
class TimerTaskList implements Delayed {

	public TimerTaskList(AtomicInteger taskCounter) {
		this.taskCounter = taskCounter;
		root.setNext(root);
		root.setPrev(root);
	}

	// TimerTaskList forms a doubly linked cyclic list using a dummy root entry
	// root.next points to the head
	// root.prev points to the tail
	private TimerTaskEntry root = new TimerTaskEntry(null, -1);

	private AtomicInteger taskCounter;

	private AtomicLong expiration = new AtomicLong(-1L);

	public boolean setExpiration(Long expirationMs) {
		return this.expiration.getAndSet(expirationMs) != expirationMs;
	}

	public Long getExpiration() {
		return expiration.get();
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
					if (timerTaskEntry.getList() == null) {
						// put the timer task entry to the end of the list. (root.prev points to the
						// tail entry)
						TimerTaskEntry tail = root.getPrev();
						timerTaskEntry.setNext(root);
						timerTaskEntry.setPrev(tail);
						timerTaskEntry.setList(this);
						tail.setNext(timerTaskEntry);
						root.setPrev(timerTaskEntry);
						taskCounter.incrementAndGet();
						done = true;
					}
				}
			}
		}
	}

	public void remove(TimerTaskEntry timerTaskEntry) {
		synchronized (this) {
			synchronized (timerTaskEntry) {
				if (timerTaskEntry.getList().equals(this)) {
					timerTaskEntry.getNext().setPrev(timerTaskEntry.getPrev());
					timerTaskEntry.getPrev().setNext(timerTaskEntry.getNext());
					timerTaskEntry.setNext(null);
					timerTaskEntry.setPrev(null);
					timerTaskEntry.setList(null);
					taskCounter.decrementAndGet();
				}
			}
		}

	}
	
	
	  // Remove all task entries and apply the supplied function to each of them
	  public void  flush(SystemTimer timer){
	    synchronized(this) {
	    TimerTaskEntry head = root.getNext();
	      while (head != root) {
	        remove(head);
	        timer.addTimerTaskEntry(head);
	        head = root.getNext();
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

}

class TimerTaskEntry implements Comparable<TimerTaskEntry> {
	private volatile TimerTaskList list;
	private TimerTaskEntry next;
	private TimerTaskEntry prev;
	private TimerTask timerTask;
	private Long expirationMs;

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
		System.out.println(o.expirationMs);
		return this.expirationMs.compareTo(o.expirationMs);
	}

	public TimerTaskList getList() {
		return list;
	}

	public void setList(TimerTaskList list) {
		this.list = list;
	}

	public TimerTaskEntry getNext() {
		return next;
	}

	public void setNext(TimerTaskEntry next) {
		this.next = next;
	}

	public TimerTaskEntry getPrev() {
		return prev;
	}

	public void setPrev(TimerTaskEntry prev) {
		this.prev = prev;
	}

	public TimerTask getTimerTask() {
		return timerTask;
	}

	public void setTimerTask(TimerTask timerTask) {
		this.timerTask = timerTask;
	}

	public Long getExpirationMs() {
		return expirationMs;
	}

	public void setExpirationMs(Long expirationMs) {
		this.expirationMs = expirationMs;
	}

}