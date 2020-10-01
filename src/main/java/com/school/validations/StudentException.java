package com.school.validations;

/**
 * @author Branislav
 *
 */
public class StudentException extends Exception {
 
	private static final long serialVersionUID = 1L;
	private String message;
	
	/**
	 * @param message must not be null
	 */
	public StudentException(String message) {
		this.message=message;
	}
	/**
	 *@return message
	 */
	@Override
	public String getMessage() {
		
		return this.message;
	}



}
