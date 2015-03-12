package com.sendsafely.exceptions;

public class CredentialsException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public CredentialsException()
	{
		super();
		error = "unknown";
	}
	
	public CredentialsException(String err){
		super(err);
		error = err;
	}
	
	public CredentialsException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
