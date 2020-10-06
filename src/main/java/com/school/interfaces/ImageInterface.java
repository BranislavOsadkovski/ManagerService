/**
 * Copyright the original author or authors.
 */
package com.school.interfaces;

/**
 * @author Branislav
 *
 */
public interface ImageInterface {

	public void  setImageId(int id);
	public void setImageBytes(byte [] bytes);
	public int getImageId();
	public byte[] getImageBytes();
}
