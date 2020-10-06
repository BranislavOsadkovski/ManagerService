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
	public RImage(int id,StudentJDBCTemplate template) { 
		 
		this.image = new Image();
		this.template= template;
		this.image.setImageBytes(this.template.getStudentImage(id));
		image.setImageId(id);
	 
	}

	public Image getRealImage() { 
		return this.image;
	}
}
