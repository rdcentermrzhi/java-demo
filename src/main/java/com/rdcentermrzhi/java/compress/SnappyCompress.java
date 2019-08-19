package com.rdcentermrzhi.java.compress;

import java.io.IOException;

import org.xerial.snappy.Snappy;

import com.rdcentermrzhi.java.demo.proto.SerializeDemo.Student;

public class SnappyCompress {
	public static void main(String[] args) throws IOException
	{
		Student.Builder builder =  Student.newBuilder();
		builder.setA(12);
		builder.setB(4000);
		builder.setD("abcefgfdsafdsa safdsadf asdfsaffsdfsdddddddddddd"
				+ "  safsad sadfdddffffffffffasase");
		builder.setG(122233);
		builder.setF(12.6f);
		byte[] before = builder.build().toByteArray();
		byte[]  after =	Snappy.compress(before);
		
		
		System.out.println(before.length + " " + after.length);
	}

}
