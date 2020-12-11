/**
 * Copyright the original author or authors.
 */
package com.school.factories;

import com.school.validations.OcupationType;
import com.school.interfaces.Ocupation;
import com.school.objects.Student;

/**
 * Factory Class that produces Objects that implement Ocupation interface
 * 
 * @author Branislav
 *
 */

public class OcupationFactory extends AbstractFactory {

	/**
	 * returns new Object instance that implements Ocupation interface
	 * 
	 * @param type
	 * @return Ocupation
	 */
	@Override
	public Ocupation getOcupation(OcupationType type) {
		if (type == OcupationType.STUDENT) {
			return new Student();
		}
		return null;
	}

}
