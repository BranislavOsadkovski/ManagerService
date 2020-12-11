/**
 * Copyright the original author or authors.
 */
package com.school.objects;

import com.school.interfaces.ImageInterface;

/**
 * Image class to represent Image object
 * 
 * @author Branislav
 *
 */
public class Image implements ImageInterface {

	private int id;
	private byte[] imageBytes;
 
	/**
	 * @param id2
	 * @param bytes must not be null
	 */
	public Image(int id2, byte[] bytes) {
		this.id = id2;
		this.imageBytes = bytes;
	}

	/**
	 * must not be null
	 */
	public Image() {
	}

	/**
	 * @param id to set
	 */
	@Override
	public void setImageId(int id) {
		this.id = id;

	}

	/**
	 * @param bytes to set
	 */
	@Override
	public void setImageBytes(byte[] bytes) {
		this.imageBytes = bytes;
	}

	/**
	 * returns id
	 *
	 * @return id
	 */
	@Override
	public int getImageId() { 
		return this.id; 
	}

	/**
	 * returns image bytes
	 *
	 * @return imageBytes
	 */
	@Override
	public byte[] getImageBytes() {

		return this.imageBytes;
	}

}
