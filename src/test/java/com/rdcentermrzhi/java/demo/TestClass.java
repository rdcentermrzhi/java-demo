package com.rdcentermrzhi.java.demo;

public class TestClass {
	private Long a;
	private Integer b;
	
	private long curr =  System.currentTimeMillis();
	
	
	public long getCurr() {
		return curr;
	}



	public Long getA() {
		return a;
	}



	public void setA(Long a) {
		this.a = a;
	}



	public Integer getB() {
		return b;
	}



	public void setB(Integer b) {
		this.b = b;
	}



	public void setCurr(long curr) {
		this.curr = curr;
	}



	public TestClass() {
		
	}
	
	
	
	public void pr(TestClass c) {
		System.out.println(c.a);
	}
	
	
	
}
