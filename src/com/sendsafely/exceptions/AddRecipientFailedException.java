package com.sendsafely.exceptions;

public class AddRecipientFailedException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public AddRecipientFailedException()
	{
		super();
		error = "unknown";
	}
	
	public AddRecipientFailedException(String err){
		super(err);
		error = err;
	}
	
	public AddRecipientFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
