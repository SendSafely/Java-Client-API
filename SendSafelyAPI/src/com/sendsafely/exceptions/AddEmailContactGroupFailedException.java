package com.sendsafely.exceptions;

public class AddEmailContactGroupFailedException extends Exception {
private static final long serialVersionUID = 1L;
	
	String error;
	
	public AddEmailContactGroupFailedException()
	{
		super();
		error = "unknown";
	}
	
	public AddEmailContactGroupFailedException(String err){
		super(err);
		error = err;
	}
	
	public AddEmailContactGroupFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
