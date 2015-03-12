package com.sendsafely.exceptions;

public class TokenGenerationFailedException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public TokenGenerationFailedException()
	{
		super();
		error = "unknown";
	}
	
	public TokenGenerationFailedException(String err){
		super(err);
		error = err;
	}
	
	public TokenGenerationFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
