/**
 * Copyright the original author or authors.
 */
package com.school.objects;

/**
 * @author Branislav
 *
 */
public interface Image {

	public void  setImageId(int id);
	public void setImageBytes(byte [] bytes);
	public int getImageId();
	public byte[] getImageBytes();
}
