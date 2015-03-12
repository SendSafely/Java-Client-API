package com.sendsafely.exceptions;

public class SendFailedException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public SendFailedException()
	{
		super();
		error = "unknown";
	}
	
	public SendFailedException(String err){
		super(err);
		error = err;
	}
	
	public SendFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
