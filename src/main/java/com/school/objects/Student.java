package com.school.objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Student class to represent Student object
 * 
 * @author Branislav
 *
 */
@XmlRootElement(name = "student")
public class Student implements Ocupation {
	
	private int id;
	private String name;
	private int age;
	private String email;
	private byte[] image;

	/**
	 * Creates Student
	 * 
	 */
	public Student() {
	}

	/**
	 * Creates Student
	 * 
	 * @param name
	 * @param age
	 * @param email
	 * @param image
	 */
	public Student(String name, int age, String email, byte[] image) {
		this.name = name;
		this.age = age;
		this.email = email;
		this.image = image;
	}

	/**
	 * Creates Student
	 * 
	 * @param id
	 * @param name
	 * @param age
	 * @param email
	 * @param image
	 */
	public Student(int id, String name, int age, String email, byte[] image) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.email = email;
		this.image = image;
	}

	/**
	 * Set the id
	 * 
	 * @param id
	 */
	@XmlElement
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set the name
	 * 
	 * @param name
	 */
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the age
	 * 
	 * @param age
	 */
	@XmlElement
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Set the email
	 * 
	 * @param email
	 */
	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set the image
	 * 
	 * @param image
	 */
	@XmlElement
	public void setImage(byte[] image) {
		this.image = image;
	}

	/**
	 * @return image
	 */
	public byte[] getImage() {
		return image;
	} 

}
