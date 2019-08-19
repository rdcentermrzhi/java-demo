package com.rdcentermrzhi.java.demo.kryo;

import java.io.RandomAccessFile;

import com.rdcentermrzhi.java.demo.proto.SerializeDemo;

public class TestKyro {
	public static void main(String[] args) throws Exception {
		
		/*long t = System.currentTimeMillis();
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
		System.out.println(list);*/
		
		//File f =  new File("a.txt");
		
		
		
		RandomAccessFile rf = new RandomAccessFile("a.txt", "rw");
		long t =  System.currentTimeMillis();
		//for(int i = 1; i < 1000; i++ )
		//{
			//Student stu =	new Student(1, 2, 3, "ccc", 1, 1);
		
			SerializeDemo.Student.Builder  str = SerializeDemo.Student.newBuilder().setA(1).setB(2).setC(3).setD("ccc").setF(1).setG(1); 
			byte[] b = str.build().toByteArray();//  KryoSerializationUtils.kryoSerialize(stu);
			
			rf.write(b);
		
			
			System.out.println(b);
		//}
		rf.close();
		long t1 =  System.currentTimeMillis();
		
		System.out.println(t1 - t);
		
	}
	
}

