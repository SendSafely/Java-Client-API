package com.sendsafely.exceptions;

public class DirectoryOperationFailedException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public DirectoryOperationFailedException()
	{
		super();
		error = "unknown";
	}
	
	public DirectoryOperationFailedException(String err){
		super(err);
		error = err;
	}
	
	public DirectoryOperationFailedException(Exception e){
		super(e);
		error = e.getMessage();
	}
	
	public String getError()
	{
		return error;
	}
}
