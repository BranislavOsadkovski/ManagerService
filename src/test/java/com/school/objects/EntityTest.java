/**
 * Copyright the original author or authors.
 */
package com.school.objects;

import org.junit.Before;
import org.junit.Test; 


import junit.framework.Assert;

/**
 * @author Branislav
 *
 */
public class EntityTest {

	int STUDENT_ID = 1;
	String STUDENT_NAME = "test_name";
	int STUDENT_AGE = 20;
	String STUDENT_EMAIL = "test_email";
	byte[] STUDENT_IMAGE = { 1, 1, 1, 0, 1, 1, 0 };

	int IMAGE_ID = 1;
	byte[] IMAGE_BYTES = { 1, 1, 0, 0 };

	Student test_student = null;
	ImageImpl test_image = null;

	@Before
	public void atStart() {
		test_student = new Student(STUDENT_ID, STUDENT_NAME, STUDENT_AGE, STUDENT_EMAIL, STUDENT_IMAGE);
		test_image = new ImageImpl(IMAGE_ID, IMAGE_BYTES);
	}
	
	@Test
	public void testStudent() { 
		Student student = new Student(1,"test_name",20,"test_email",null);
		
		Assert.assertEquals(student.getId(), test_student.getId());
		Assert.assertEquals(student.getName(), test_student.getName());
		Assert.assertEquals(student.getAge(),test_student.getAge());
		Assert.assertEquals(student.getEmail(),test_student.getEmail());
		Assert.assertNull(student.getImage());
		Assert.assertNotNull(test_student.getImage());
		
	}
	
	@Test
	public void testImage() {
		ImageImpl image = new ImageImpl(1,null);
		
		Assert.assertEquals(image.getImageId(), test_image.getImageId());
		Assert.assertNull(image.getImageBytes());
		Assert.assertNotNull(test_image.getImageBytes());
	}

}
