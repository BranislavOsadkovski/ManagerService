package com.school.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentValidator {
	private static boolean valid = false;
	private static Pattern pattern;
	private static Matcher matcher;

	public static boolean validateStudent(String name, String age, String email) throws StudentException {
		valid = validateEmail(email);
		valid = validateAge(age);
		valid = validateName(name);
		return valid;
	}

	public static boolean validateStudent(String id, String name, String age, String email) throws StudentException {
		valid = validateId(id);
		valid = validateEmail(email);
		valid = validateAge(age);
		valid = validateName(name);

		return valid;
	}

	public static boolean validateEmail(String email) throws StudentException {
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

	public static boolean validateAge(String age) throws StudentException {
		pattern = Pattern.compile("^[0-9]*$");
		matcher = pattern.matcher(age);
		if (age == null) {
			valid = false;
			throw new StudentException("Age value can not be null; Must be a number;");
		} else if (age.isEmpty()) {
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

	public static boolean validateId(String id) throws StudentException {
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

	public static boolean validateName(String name) throws StudentException {

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

	public static boolean validatePathName(String name) throws StudentException {

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
