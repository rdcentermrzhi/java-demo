package com.rdcentermrzhi.java.demo;

import java.util.HashMap;
import java.util.Map;

public class ClassAccessControl {
	
	private int control  = 1;
	
	private Map<String,String> map = new HashMap<String,String>();
	
	
	
	public void test(ClassAccessControl a) {
		this.control = a.control;
		this.map = a.map;
	}
	
	public void test(SonP a) {
		//this.control = a.as;
		
	}
	
	

}
