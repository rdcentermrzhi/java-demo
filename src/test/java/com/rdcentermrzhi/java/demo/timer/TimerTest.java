package com.rdcentermrzhi.java.demo.timer;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TimerTest {

	private Timer timer = null;

	@Before
	public void setUp() {
		timer = new SystemTimer("test", 50L, 12);

	}

	@After
	public void teardown() {
		timer.shutdown();
	}

	//@Test
	public void testAlreadyExpiredTask() throws InterruptedException {
		List<Integer> output = new ArrayList<>();

		TreeMap<Integer, CountDownLatch> latches = new TreeMap<>();
		for (int i = -5; i < 0; i++) {
			CountDownLatch latch = new CountDownLatch(1);
			timer.add(new TestTask(i, i, latch, output));
			latches.put(i, latch);
		}

		timer.advanceClock(0L);

		for (int i = -5; i < 0; i++) {
			CountDownLatch latch = latches.get(i);
			assertEquals("already expired tasks should run immediately", true, latch.await(3, TimeUnit.SECONDS));
		}

		// assertEquals("output of already expired tasks", new int[] {-5, -4, -3, -2,
		// -1}, output.toArray());

	}

	@Test
	public void testTaskExpiration() throws InterruptedException {

		List<Integer> output = new ArrayList<>();

		TreeMap<Integer, CountDownLatch> latches = new TreeMap<>();
		for (int i = 500; i < 1600 ; i += 500) {
			CountDownLatch latch = new CountDownLatch(1);
			timer.add(new TestTask(i, i, latch, output));
			latches.put(i, latch);
		}

		/*int j = 0;
		//推进时间
		while(timer.advanceClock(5L)) {
			System.out.println(j++);
		};*/

		new Thread(() -> {
			for(;;) {
				timer.advanceClock(5L);
			}
		}).start();
		
		for (int i = 500; i < 1600 ; i += 500) {
			CountDownLatch latch = latches.get(i);
			assertEquals("output should match", true, latch.await(3, TimeUnit.SECONDS));
		}
	}
	
	
	

}

class TestTask extends TimerTask {

	
	public int id;

	public CountDownLatch latch;

	public List<Integer> output;

	public AtomicBoolean completed = new AtomicBoolean(false);

	public TestTask(long delayMs, int id, CountDownLatch latch, List<Integer> output) {
		super.delayMs = delayMs;
		this.id = id;
		this.latch = latch;
		this.output = output;
	}

	@Override
	public void run() {
		
		System.out.println("##########" + id+ "\t" + System.currentTimeMillis());
		if (completed.compareAndSet(false, true)) {
			synchronized (output) {
				output.add(id);
				latch.countDown();
			}
		}

	}

}
