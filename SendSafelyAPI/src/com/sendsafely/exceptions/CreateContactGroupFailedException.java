package com.sendsafely.exceptions;

public class CreateContactGroupFailedException extends Exception {

	private static final long serialVersionUID = 1L;
	
	String error;
	
	public CreateContactGroupFailedException()
	{
		super();
		error = "unknown";
	}
	
	public CreateContactGroupFailedException(String err){
		super(err);
		error = err;
	}
	
	public CreateContactGroupFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
