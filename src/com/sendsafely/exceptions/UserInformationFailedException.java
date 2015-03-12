package com.sendsafely.exceptions;

public class UserInformationFailedException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public UserInformationFailedException()
	{
		super();
		error = "unknown";
	}
	
	public UserInformationFailedException(String err){
		super(err);
		error = err;
	}
	
	public UserInformationFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
