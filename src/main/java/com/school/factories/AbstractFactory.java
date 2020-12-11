/**
 * Copyright the original author or authors.
 */
package com.school.factories;

import com.school.objects.Ocupation;
import com.school.validations.OcupationType;

/**
 * Abstract implementation of a base class used for OcupationFactories
 * 
 * @author Branislav
 *
 */
public abstract class AbstractFactory { 
	public abstract Ocupation getOcupation(OcupationType type);
	
}
