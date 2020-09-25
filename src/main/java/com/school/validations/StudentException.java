package com.school.validations;

public class StudentException extends Exception {
	  
	private String message;
	
	public StudentException(String message) {
		this.message=message;
	}
	@Override
	public String getMessage() {
		
		return this.message;
	}

	private static final long serialVersionUID = 1L;

}
