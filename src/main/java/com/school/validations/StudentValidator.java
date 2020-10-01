package com.school.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

/**
 * StudentValidator Class contains static methods used to check if Student object and Student fields are valid or not. 
 * Throws StudentException if not.
 * @author Branislav
 *
 */
public class StudentValidator {
	private static boolean valid = false;
	private static Pattern pattern;
	private static Matcher matcher;

	/**
	 * Check if Student fields are valid if not throws StudentExpcetion
	 * @param name
	 * @param age
	 * @param email
	 * @return valid
	 * @throws StudentException
	 */
	public static boolean validateStudent(String name, String age, String email) throws StudentException {
		valid = validateEmail(email);
		valid = validateAge(age);
		valid = validateName(name);
		return valid;
	}

	/**
	 * Check if Student is valid if not throws StudentException
	 * @param id
	 * @param name
	 * @param age
	 * @param email
	 * @return valid
	 * @throws StudentException
	 */
	public static boolean validateStudent(String id, String name, String age, String email) throws StudentException {
		valid = validateId(id);
		valid = validateEmail(email);
		valid = validateAge(age);
		valid = validateName(name);

		return valid;
	}

	/**
	 * Check if email is valid if not throws StudentException
	 * @param email
	 * @return valid
	 * @throws StudentException
	 */
	public static boolean validateEmail(String email) throws StudentException {
		Assert.notNull(email,"email can not be null");
		pattern = Pattern.compile("^(.+)@(.+)$");
		matcher = pattern.matcher(email);
		if (!matcher.find()) {
			valid = false;
			throw new StudentException("Invalid email;");
		} else {
			valid = true;
		}

		return valid;
	}

	/**
 	 * Check if age is valid if not throws StudentException
	 * @param age
	 * @return valid
	 * @throws StudentException
	 */
	public static boolean validateAge(String age) throws StudentException {
		Assert.notNull(age,"age can not be null");
		pattern = Pattern.compile("^[0-9]*$");
		matcher = pattern.matcher(age);
		 if (age.isEmpty()) {
			valid = false;
			throw new StudentException("Age value can not be empty;");
		} else if (!matcher.find()) {
			valid = false;
			throw new StudentException("Age value can not contain characters; Must be a number;");
		} else {
			valid = true;
		}
		return valid;
	}

	/**
 	 * Check if id is valid if not throws StudentException
	 * @param id
	 * @return valid
	 * @throws StudentException
	 */
	public static boolean validateId(String id) throws StudentException {
		Assert.notNull(id,"id can not be null");
		pattern = Pattern.compile("^[0-9]*$");
		matcher = pattern.matcher(id);
		if (!matcher.find()) {
			valid = false;
			throw new StudentException("Id value can not contain characters; Must be a number;");
		} else if (id == null) {
			valid = false;
			throw new StudentException("Id value can not be null; Must be a number;");
		} else {
			valid = true;
		}
		return valid;
	}

	/**
 	 * Check if name is valid if not throws StudentException  
	 * @param name
	 * @return valid
	 * @throws StudentException
	 */
	public static boolean validateName(String name) throws StudentException {
		Assert.notNull(name,"name can not be null");
		pattern = Pattern.compile("/^[A-Z]+[a-zA-Z]");
		matcher = pattern.matcher(name);
		if (matcher.find()) {
			valid = false;
			throw new StudentException("Name can contain only characters; First character must be uppercase;");
		} else {
			valid = true;
		}

		return valid;
	}

	/**
  	 * Check if name is valid if not throws StudentException  
	 * @param name
	 * @return valid
	 * @throws StudentException
	 */
	public static boolean validatePathName(String name) throws StudentException {
		Assert.notNull(name,"name can not be null");
		pattern = Pattern.compile("a-zA-Z");
		matcher = pattern.matcher(name);
		if (matcher.find()) {
			valid = false;
			throw new StudentException("Name can contain only characters;");
		} else {
			valid = true;
		}

		return valid;
	}
 
}
