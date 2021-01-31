/**
 * Copyright the original author or authors.
 */
package com.school.factories;

import org.junit.Before;
import org.junit.Test;

import com.school.objects.Ocupation;
import com.school.objects.Student;
import com.school.validations.OcupationType;

import junit.framework.Assert;

/**
 * @author Branislav
 *
 */
public class StudentFactoryTest {

	
	OcupationType STUDENT_TYPE = OcupationType.STUDENT;
	OcupationFactory factory=null;
	Student test_student = new Student();
	
	@Before
	public void atStart() {
		 factory = new OcupationFactory();
	}
	
	@Test
	public void testStudentType() {
		Ocupation student = (Student) factory.getOcupation(STUDENT_TYPE); 
		Assert.assertEquals(student.getClass(), test_student.getClass());
	}
}
