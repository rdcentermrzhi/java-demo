package com.rdcentermrzhi.java.demo.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Jdk8Stream {
	public static Student st;

	public static void main(String[] args) {

		List<Student> students = new ArrayList<>();
		students.add(new Student("abc", 14));
		students.add(new Student("cfg", 11));
		students.add(new Student("cfg", 16));

		//students.stream().map(mapper);

		/*long count =  students.parallelStream().filter(student -> {
			int a = student.getAge();
			return  a > 10;
		}).limit(3).count();*/
		int i = 0;
		students.forEach((student) ->{
			 	 System.out.println(student.getAge());
		});
		
		
		//移除符合条件的数据
		students.removeIf(student ->student.getAge()>11);
		
		System.out.println(students);
		//System.out.println(count);
		
		
		IntStream.of(new int[]{1, 2, 3}).forEach(System.out::println);
		
		//Jdk8Stream.st::getAge;
		
		
	/*	下面来说一下方法引用，方法引用是lambda表达式的一种简写形式。 如果lambda表达式只是调用一个特定的已经存在的方法，则可以使用方法引用。
		使用“::”操作符将方法名和对象或类的名字分隔开来。以下是四种使用情况：
		对象::实例方法
		类::静态方法
		类::实例方法
		类::new*/
		
		
	
		
		

	}
}
