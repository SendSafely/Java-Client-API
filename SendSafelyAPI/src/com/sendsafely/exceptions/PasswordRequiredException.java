package com.sendsafely.exceptions;

public class PasswordRequiredException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public PasswordRequiredException()
	{
		super();
		error = "unknown";
	}
	
	public PasswordRequiredException(String err){
		super(err);
		error = err;
	}
	
	public PasswordRequiredException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
