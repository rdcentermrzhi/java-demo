package com.rdcentermrzhi.java.demo;

public class P {

	public int xx = 0;
	
	public int getXx() {
		return xx;
	}

	public void setXx(int xx) {
		this.xx = xx;
	}


	private int age;
	private String name;
	public P(int age, String name) {
		super();
		this.age = age;
		this.name = name;
	};
	
	public P(int age) {
		this(1,"");
	}
	
	
	public P() {
		
	}
	
}
