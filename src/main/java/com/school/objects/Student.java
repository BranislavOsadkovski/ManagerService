package com.school.objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "student")
public class Student {
	private int id;
	private String name;
	private int age;	
	private String email;
	private byte[] image;
	
	@XmlElement
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	@XmlElement
	public void setAge(int age) {
		this.age = age;
	}
	public int getAge() {
		return age;
	}
	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}	
	public String getEmail() {
		return email;
	}

	@XmlElement
	public void setImage(byte[] image) {
		this.image = image;
	}

	public byte[] getImage() {
		return image;
	}


	
	
	
	
}
