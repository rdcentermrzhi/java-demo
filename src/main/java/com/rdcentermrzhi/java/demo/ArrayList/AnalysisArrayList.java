package com.rdcentermrzhi.java.demo.ArrayList;

import java.util.Arrays;

public class AnalysisArrayList {

	public static void main(String[] args) {
		
		
		 //数组copy
		Object[] obj = {1,2,3,4,5,6,7};
		Object[] newObj = Arrays.copyOf(obj, 4);
		
		
		System.out.println(Arrays.toString(newObj));
		
		
	}
}
