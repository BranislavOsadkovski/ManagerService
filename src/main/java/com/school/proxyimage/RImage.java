/**
 * Copyright the original author or authors.
 */
package com.school.proxyimage;

import com.school.objects.Image;
import com.school.util.StudentJDBCTemplate;

/**
 * @author Branislav
 *
 */
public class RImage {

	private Image image;
	private StudentJDBCTemplate template;

	/**
	 * When initialized fetches image from database and stores it in Image Object
	 * that can be accessed from the method getRealImage();
	 * 
	 * @param id
	 * @param template must not be null
	 */
	public RImage(int id, StudentJDBCTemplate template) {

		this.image = new Image();
		this.template = template;
		this.image.setImageBytes(this.template.getImage(id));
		image.setImageId(id);

	}

	/**
	 * returns image object
	 * 
	 * @return image
	 */
	public Image getRealImage() {
		return this.image;
	}
}
