package com.rdcentermrzhi.java.demo.time;

public class TimeUsDemo {

	public static void main(String[] args) {
		// java 实现微秒
		System.out.println(timeUsDemo());
	}

	public static long timeUsDemo() {
		long cuTimeMills = System.currentTimeMillis() * 1000;
		long nanoTime = System.nanoTime();

		long x = cuTimeMills + (nanoTime - nanoTime / 1000000L * 1000000L) / 1000L;

		System.out.println(x +"\t" + cuTimeMills);
		return x;
	}
}
