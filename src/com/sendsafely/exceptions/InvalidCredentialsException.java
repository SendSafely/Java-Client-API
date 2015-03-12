package com.sendsafely.exceptions;

public class InvalidCredentialsException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public InvalidCredentialsException()
	{
		super();
		error = "unknown";
	}
	
	public InvalidCredentialsException(String err){
		super(err);
		error = err;
	}
	
	public InvalidCredentialsException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
