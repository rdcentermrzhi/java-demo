package com.rdcentermrzhi.java.demo.kryo.kyro;

public class MPlayer{
	private String name;
	private int age;
	
	
	
	public MPlayer() {
		
	}
	
	public MPlayer(String name, int age)
	{
		this.name =  name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "MPlayer [name=" + name + ", age=" + age + "]";
	}
	
	
}

