package com.rdcentermrzhi.java.demo.kryo.kyro;

import java.util.ArrayList;
import java.util.List;


public class TestKyro {
	public static void main(String[] args) {
		
		long t = System.currentTimeMillis();
		List<MPlayer> list = new ArrayList<>();
		
		list.add(new MPlayer("adb",12));
		list.add(new MPlayer("ad3",13));
		list.add(new MPlayer("ad4",14));
		
		byte[] b =KryoSerializationUtils.kryoSerialize(list);
		
		list = (List<MPlayer>) KryoSerializationUtils.kryoDeserialize(b);
		long t2 = System.currentTimeMillis();
		System.out.println(t2 - t );
		
		
		byte[] b1 =KryoSerializationUtils.kryoSerialize(list);
		
		list = (List<MPlayer>) KryoSerializationUtils.kryoDeserialize(b1);
		System.out.println(System.currentTimeMillis() - t2);
		System.out.println(list);
	}
	
}

