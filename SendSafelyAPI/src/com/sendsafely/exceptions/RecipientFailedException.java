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
	
	public RecipientFailedException(String err, Exception e){
		super(err, e);
		error = err;
	}
	
	public String getError()
	{
		return error;
	}
}
