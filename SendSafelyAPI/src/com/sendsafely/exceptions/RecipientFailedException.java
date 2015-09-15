package com.sendsafely.exceptions;

public class RecipientFailedException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public RecipientFailedException()
	{
		super();
		error = "unknown";
	}
	
	public RecipientFailedException(String err){
		super(err);
		error = err;
	}
	
	public RecipientFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
