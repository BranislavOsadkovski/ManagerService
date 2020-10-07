/**
 * Copyright the original author or authors.
 */
package com.school.interfaces;

/**
 * Interface Ocupation
 * 
 * @author Branislav
 *
 */
public interface Ocupation {

	public void setId(int id);

	/**
	 * @return id
	 */
	public int getId();

	/**
	 * Set the name
	 * 
	 * @param name
	 */

	public void setName(String name);

	/**
	 * @return name
	 */
	public String getName();

	/**
	 * Set the age
	 * 
	 * @param age
	 */

	public void setAge(int age);

	/**
	 * @return age
	 */
	public int getAge();

	/**
	 * Set the email
	 * 
	 * @param email
	 */

	public void setEmail(String email);

	/**
	 * @return email
	 */
	public String getEmail();

	/**
	 * Set the image
	 * 
	 * @param image
	 */

	public void setImage(byte[] image);

	/**
	 * @return image
	 */
	public byte[] getImage();
}
