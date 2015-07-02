package com.sendsafely.exceptions;

public class LimitExceededException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public LimitExceededException()
	{
		super();
		error = "unknown";
	}
	
	public LimitExceededException(String err){
		super(err);
		error = err;
	}
	
	public LimitExceededException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
