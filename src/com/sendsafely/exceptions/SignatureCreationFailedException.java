package com.sendsafely.exceptions;

public class SignatureCreationFailedException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public SignatureCreationFailedException()
	{
		super();
		error = "unknown";
	}
	
	public SignatureCreationFailedException(String err){
		super(err);
		error = err;
	}
	
	public SignatureCreationFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
